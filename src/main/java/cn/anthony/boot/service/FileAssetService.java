package cn.anthony.boot.service;

import cn.anthony.boot.domain.FileAsset;
import cn.anthony.boot.domain.QFileAsset;
import cn.anthony.boot.repository.FileAssetRepository;
import cn.anthony.boot.util.Constant;
import com.alibaba.fastjson.JSONObject;
import com.hankcs.hanlp.HanLP;
import com.hankcs.hanlp.corpus.tag.Nature;
import com.hankcs.hanlp.seg.Segment;
import com.hankcs.hanlp.seg.common.Term;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.aggregation.Aggregation;
import org.springframework.data.mongodb.core.aggregation.AggregationResults;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.io.File;
import java.util.List;

@Service
public class FileAssetService extends GenericService<FileAsset, QFileAsset> {
	@Resource
	protected FileAssetRepository repository;
	@Autowired
	private MongoTemplate mongoTemplate;
	private static Segment segment = HanLP.newSegment().enableNameRecognize(true);

	@Override
	public FileAssetRepository getRepository() {
		return repository;
	}

	public List<FileAsset> findByNr(String nr) {
		return repository.findByNr(nr);
	}

	public Page findDistinctNr(Pageable pageable) {
		//Criteria criteria = new Criteria();
		Query query = new Query();
		//query.addCriteria(criteria);
		query.with(pageable);
		query.limit(pageable.getPageSize());
		//List<String> l = mongoTemplate.getCollection("fileAsset").distinct("nr", query.getQueryObject());
		Aggregation agg = Aggregation.newAggregation(
				// 挑选所需的字段，类似select *，*所代表的字段内容
				Aggregation.project("activeCode", "startDate","packetType"),
				// sql where 语句筛选符合条件的记录
				//Aggregation.match(Criteria.where("deviceId").is(getListParam.getDeviceId())),
				// 分组条件，设置分组字段
				Aggregation.group("nr").first("nr").as("nr"),
				// 排序（根据某字段排序 倒序）
				Aggregation.sort(Sort.Direction.ASC, "nr"),
				// 重新挑选字段
				Aggregation.project("activeCode")
		);
		AggregationResults<JSONObject> results = mongoTemplate.aggregate(agg, "fileAsset", JSONObject.class);
		List<JSONObject> l = results.getMappedResults();

		int from = (int)pageable.getOffset();
		int to = from+pageable.getPageSize()>l.size()?l.size():from+pageable.getPageSize();
		//pageable.getOffset(),pageable.getOffset()+pageable.getPageSize())
		return new PageImpl(l.subList(from,to),pageable, l.size());
		//return l;
	}

	/**
	 * 从目录开始扫描，把外来资料入库
	 */
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
			asset.setVistiPath(vistPath(f.getAbsolutePath()));
			repository.save(asset);
			if (f.isDirectory())
				initNrAndSave(f);
		}
	}

	private String vistPath(String a) {
		int index = a.indexOf(":\\");
		if(index>0) {
			String driver = a.substring(0,a.indexOf(":\\")).toLowerCase();
			String pre = "";
			switch(driver) {
			case "u":
				 pre = "http://172.21.17.9:8001";break;
			case "v":
				pre = "http://172.21.17.9:8002";break;
			case "w":
				pre = "http://172.21.17.9:8003";break;
			case "x":
				pre = "http://172.21.17.9:8004";break;
			case "y":
				pre = "http://172.21.17.9:8005";break;
			case "z":
				pre = "http://172.21.17.9:8006";break;
			}
			driver = driver.substring(index+2);
			driver = driver.replaceAll("\\", "/");
			return pre+"/"+driver;
		}
		return null;
	}

}
