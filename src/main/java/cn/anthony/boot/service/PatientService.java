package cn.anthony.boot.service;

import javax.annotation.Resource;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import com.mysema.query.types.Predicate;

import cn.anthony.boot.domain.Patient;
import cn.anthony.boot.repository.PatientRepository;

@Service
public class PatientService extends GenericService<Patient> {
    @Resource
    protected PatientRepository repository;

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
}
