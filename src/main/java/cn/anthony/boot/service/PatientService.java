package cn.anthony.boot.service;

import java.io.File;
import java.util.Date;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import javax.annotation.Resource;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.mongodb.core.MongoOperations;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.stereotype.Service;

import com.querydsl.core.types.Predicate;

import cn.anthony.boot.domain.Patient;
import cn.anthony.boot.repository.PatientRepository;
import cn.anthony.boot.util.PatientUtil;
import cn.anthony.util.FileTools;

@SuppressWarnings("rawtypes")
@Service
public class PatientService extends GenericService<Patient> {
	@Resource
	protected PatientRepository repository;
	@Autowired
	private MongoOperations mongoTemplate;
	private static List<String> changedeptList;
	private static List<String> dischargeWardList;
	private static List<String> admissionWardList;
	
	private static List<String> rootDirs = Arrays.asList("U:\\","V:\\","W:\\","X:\\","Y:\\","Z:\\");
	public PatientService() {
		super();
	}
	
	public String updateAssets(String source,String pId) {
		if(pId!=null)
			return updatePatientAset(repository.findByPId(pId));
		else { 
			StringBuilder sb = new StringBuilder();
			for(Patient p : repository.findBySource(source)) {
				sb.append(updatePatientAset(p));
			}
			return sb.toString();
		}
	}
	
	public String updateAssetsByInTime(String source,Date begin,Date end) {
		StringBuilder sb = new StringBuilder();
		for(Patient p : repository.findBySourceAndFrontRecordsAdmissionTimeBetween(source,begin,end)) {
			System.out.println(p.getName()+"  \n");
			sb.append(updatePatientAset(p));
		}
		return sb.toString();
	}

	private String updatePatientAset(Patient p) {
		StringBuilder sb = new StringBuilder();
		for(String dir : rootDirs)
		for(File f : FileTools.search(new File(dir),p.getName())){
			String ext = f.getPath().substring(f.getPath().lastIndexOf(".")+1);
			String type = PatientUtil.assetType(ext);
			sb.append("  "+ext+" | "+f.getAbsolutePath()+"  \n");
			p.addAsset(type, f.getAbsolutePath());
			repository.save(p);
		}
		return sb.toString();
	}
	public List<String> getChangedeptList() {
		if (changedeptList == null)
			changedeptList = ditinctChangedept();
		Collections.sort(changedeptList);
		return changedeptList;
	}

	public List<String> getDischargeWardList() {
		if (dischargeWardList == null)
			dischargeWardList = ditinctDischargeWard();
		Collections.sort(dischargeWardList);
		return dischargeWardList;
	}

	public List<String> getAdmissionWardList() {
		if (admissionWardList == null)
			admissionWardList = ditinctAdmissionWard();
		Collections.sort(admissionWardList);
		return admissionWardList;
	}

	@Override
	public PatientRepository getRepository() {
		return repository;
	}

	public Patient findByPid(String pId) {
		return repository.findByPId(pId);
	}
	public Patient findBySrcFileLike(String srcFile) {
		List<Patient> l = repository.findBySrcFileLike(srcFile);
		if (l != null && l.size() > 0)
			return l.get(0);
		return null;
	}

	public Page<Patient> find(Predicate predicate, Pageable pageable) {
		return repository.findAll(predicate, pageable);
	}

	public Long total(Predicate predicate) {
		return repository.count(predicate);
	}

	public Long total() {
		return repository.count();
	}

	public Long totalIn() {
		repository.count();
		long l = 0;
		for (Patient p : repository.findAll())
			l += p.inRecords.size();
		return l;
	}

	public List<String> ditinctChangedept() {
		Criteria criteria = new Criteria();
		// criteria.where("dataset").is("d1");
		Query query = new Query();
		query.addCriteria(criteria);
		return mongoTemplate.getCollection("patient").distinct("frontRecords.changedept", query.getQueryObject());
	}

	public List<String> ditinctDischargeWard() {
		return mongoTemplate.getCollection("patient").distinct("frontRecords.dischargeWard");
	}
	
	public List<String> ditinctSource() {
		return mongoTemplate.getCollection("patient").distinct("source");
	}

	public List<String> ditinctAdmissionWard() {
		return mongoTemplate.getCollection("patient").distinct("frontRecords.admissionWard");
	}
}
