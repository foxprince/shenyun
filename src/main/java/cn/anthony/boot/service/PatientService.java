package cn.anthony.boot.service;

import cn.anthony.boot.domain.Patient;
import cn.anthony.boot.domain.QPatient;
import cn.anthony.boot.repository.PatientRepository;
import cn.anthony.boot.util.Constant;
import cn.anthony.boot.util.PatientUtil;
import cn.anthony.util.FileTools;
import com.querydsl.core.types.Predicate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.mongodb.core.MongoOperations;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.io.File;
import java.util.Collections;
import java.util.Date;
import java.util.List;

@SuppressWarnings("rawtypes")
@Service
public class PatientService extends GenericService<Patient, QPatient> {
	@Resource
	protected PatientRepository repository;
	@Autowired
	private MongoOperations mongoTemplate;
	private static List<String> changedeptList;
	private static List<String> dischargeWardList;
	private static List<String> admissionWardList;
	private static List<String> zZDoctorList;
	private static List<String> zYDoctorList;
	private static List<String> zZhenDoctorList;
	private static List<String> mainDiagList;
	
	public PatientService() {
		super();
	}
	
	/**
	 * db.patient.aggregate([ { $group: {"_id":"$name" , "number":{$sum:1}} } ])
	 * db.patient.distinct('name').length;
	 * db.patient.distinct('name').limit(10).skip(1);
	 * db.runCommand({"distinct":"patient","key":"name","query":{"ctime":/^2011-12-15/}}).values.length(); 
	 * @param pageable
	 * @return
	 */
	public List<String> findDistinctName(Pageable pageable) {
		Query query = new Query();
		query.addCriteria(Criteria.where("source").is("haitai"))
				.with(Sort.by(Sort.Order.desc("name")))
				.with(pageable);
		List<String> l = mongoTemplate.findDistinct(query, "name",  "patient",Patient.class, String.class);
		logger.info(pageable.toString()+":"+l.size());
		//return new PageImpl(l,pageable,t);
		return l;
	}
	
	public String updateAssets(String source,String pId) {
		if(pId!=null) {
			return updatePatientAset(repository.findByPId(pId));
		} else {
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
		for(String dir : Constant.MEIDA_DIRS) {
			for(File f : FileTools.search(new File(dir),p.getName())){
				String ext = f.getPath().substring(f.getPath().lastIndexOf(".")+1);
				String type = PatientUtil.assetType(ext);
				sb.append("  "+ext+" | "+f.getAbsolutePath()+"  \n");
				p.addAsset(type, f.getAbsolutePath());
				repository.save(p);
			}
		}
		return sb.toString();
	}
	public List<String> getChangedeptList() {
		if (changedeptList == null) {
			changedeptList = ditinctChangedept();
		}
		Collections.sort(changedeptList);
		return changedeptList;
	}
	public List<String> getZzDoctorList() {
		if (zZDoctorList == null) {
			zZDoctorList = ditinctZZDoctorName();
		}
		Collections.sort(zZDoctorList);
		return zZDoctorList;
	}
	public List<String> getZyDoctorList() {
		if (zYDoctorList == null) {
			zYDoctorList = ditinctZYDoctorName();
		}
		Collections.sort(zYDoctorList);
		return zYDoctorList;
	}
	public List<String> getZzhenDoctorList() {
		if (zZhenDoctorList == null) {
			zZhenDoctorList = ditinctZZhenDoctorName();
		}
		Collections.sort(zZhenDoctorList);
		return zZhenDoctorList;
	}

	public List<String> getDischargeWardList() {
		if (dischargeWardList == null) {
			dischargeWardList = ditinctDischargeWard();
		}
		Collections.sort(dischargeWardList);
		return dischargeWardList;
	}
	
	public List<String> getMainDiagList() {
		if (mainDiagList == null) {
			mainDiagList = ditinctMainDiag();
		}
		Collections.sort(mainDiagList);
		return mainDiagList;
	}

	public List<String> getAdmissionWardList() {
		if (admissionWardList == null) {
			admissionWardList = ditinctAdmissionWard();
		}
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
		if (l != null && l.size() > 0) {
			return l.get(0);
		}
		return null;
	}

	@Override
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
		for (Patient p : repository.findAll()) {
			l += p.inRecords.size();
		}
		return l;
	}

	public List<String> ditinctChangedept() {
		Criteria criteria = new Criteria();
		// criteria.where("dataset").is("d1");
		Query query = new Query();
		query.addCriteria(criteria);
		//return mongoTemplate.getCollection("patient").distinct("myfield");
		return mongoTemplate.findDistinct("name",Patient.class,String.class);
		//return mongoTemplate.getCollection("patient").distinct("name",String.class);
		//return mongoTemplate.query(Patient.class).distinct("frontRecords.changedept").all();
	}

	public List<String> ditinctDischargeWard() {
		return mongoTemplate.findDistinct("frontRecords.dischargeWard",Patient.class,String.class);
		//return mongoTemplate.query(Patient.class).distinct("frontRecords.dischargeWard").all();
	}
	public List<String> ditinctMainDiag() {
		//return mongoTemplate.query(Patient.class).distinct("frontRecords.mainDiag").all();
		return mongoTemplate.findDistinct("frontRecords.mainDiag",Patient.class,String.class);
	}
	public List<String> ditinctZZDoctorName() {
		//return mongoTemplate.query(Patient.class).distinct("frontRecords.ZZ_DOCTOR_NAME").all();
		return mongoTemplate.findDistinct("frontRecords.ZZ_DOCTOR_NAME",Patient.class,String.class);
	}
	public List<String> ditinctZYDoctorName() {
		//return mongoTemplate.query(Patient.class).distinct("frontRecords.ZY_DOCTOR_NAME").all();
		return mongoTemplate.findDistinct("frontRecords.ZY_DOCTOR_NAME",Patient.class,String.class);
	}
	public List<String> ditinctZZhenDoctorName() {
		//return mongoTemplate.query(Patient.class).distinct("frontRecords.ZZHEN_DOCTOR_NAME").all();
		return mongoTemplate.findDistinct("frontRecords.ZZHEN_DOCTOR_NAME",Patient.class,String.class);
	}
	public List<String> ditinctSource() {
		//return mongoTemplate.query(Patient.class).distinct("source").all();
		return mongoTemplate.findDistinct("source",Patient.class,String.class);
	}

	public List<String> ditinctAdmissionWard() {
		//return mongoTemplate.query(Patient.class).distinct("frontRecords.admissionWard").all();
		return mongoTemplate.findDistinct("frontRecords.admissionWard",Patient.class,String.class);
	}
}
