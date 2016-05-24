package cn.anthony.boot.service;

import java.util.Collections;
import java.util.List;

import javax.annotation.Resource;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.stereotype.Service;

import com.mysema.query.types.Predicate;

import cn.anthony.boot.domain.Patient;
import cn.anthony.boot.repository.PatientRepository;

@Service
public class PatientService extends GenericService<Patient> {
    @Resource
    protected PatientRepository repository;
    @Autowired
    private MongoTemplate mongoTemplate;

    private static List<String> changedeptList;
    private static List<String> dischargeWardList;
    private static List<String> admissionWardList;
    
    public PatientService() {
	super();
    }
    
    public List<String> getChangedeptList() {
	if(changedeptList==null)
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
    
    public PatientRepository getRepository() {
	return repository;
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

    public List<String> ditinctAdmissionWard() {
	return mongoTemplate.getCollection("patient").distinct("frontRecords.admissionWard");
    }
    
}
