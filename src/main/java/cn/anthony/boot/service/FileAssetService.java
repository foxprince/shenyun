package cn.anthony.boot.service;

import java.io.File;
import java.util.List;

import javax.annotation.Resource;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.stereotype.Service;

import com.hankcs.hanlp.HanLP;
import com.hankcs.hanlp.corpus.tag.Nature;
import com.hankcs.hanlp.seg.Segment;
import com.hankcs.hanlp.seg.common.Term;

import cn.anthony.boot.domain.FileAsset;
import cn.anthony.boot.repository.FileAssetRepository;
import cn.anthony.boot.util.Constant;

@Service
public class FileAssetService extends GenericService<FileAsset> {
	@Resource
	protected FileAssetRepository repository;
	@Autowired
	private MongoTemplate mongoTemplate;
	private static Segment segment = HanLP.newSegment().enableNameRecognize(true);

	@Override
	public PagingAndSortingRepository<FileAsset, String> getRepository() {
		return repository;
	}

	public List<FileAsset> findByNr(String nr) {
		return repository.findByNr(nr);
	}

	public Page<String> findDistinctNr(Pageable pageable) {
		//Criteria criteria = new Criteria();
		Query query = new Query();
		//query.addCriteria(criteria);
		query.with(pageable);
		query.limit(pageable.getPageSize());
		List<String> l = mongoTemplate.getCollection("fileAsset").distinct("nr", query.getQueryObject());
		int from = pageable.getOffset();
		int to = from+pageable.getPageSize()>l.size()?l.size():from+pageable.getPageSize();
		//pageable.getOffset(),pageable.getOffset()+pageable.getPageSize())
		return new PageImpl<String>(l.subList(from,to),pageable, l.size());
		//return l;
	}

	public void init() {
		for (String dir : Constant.MEIDA_DIRS) {
			File rootDir = new File(dir);
			initNrAndSave(rootDir);
		}
	}

	private void initNrAndSave(File dir) {
		for (File f : dir.listFiles()) {
			FileAsset asset = new FileAsset();
			List<Term> termList = segment.seg(f.getName());
			for (Term term : termList)
				if (term.nature.equals(Nature.nr) || term.nature.equals(Nature.nr1) || term.nature.equals(Nature.nr2))
					asset.setNr(term.word);
			asset.setFileName(f.getName());
			asset.setFilePath(f.getAbsolutePath());
			repository.save(asset);
			if (f.isDirectory())
				initNrAndSave(f);
		}
	}

}
