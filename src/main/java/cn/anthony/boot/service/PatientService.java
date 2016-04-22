package cn.anthony.boot.service;

import javax.annotation.Resource;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import cn.anthony.boot.domain.Patient;
import cn.anthony.boot.repository.PatientRepository;
import cn.anthony.boot.web.PatientSearch;

@Service
public class PatientService extends GenericService<Patient> {
    @Resource
    protected PatientRepository repository;

    public PatientRepository getRepository() {
	return repository;
    }

    @Override
    public Page<Patient> findPage(cn.anthony.boot.web.PageRequest pageRequest) {
	Pageable pageable = new PageRequest(pageRequest.getPage() - 1, pageRequest.getSize());
	if(pageRequest instanceof PatientSearch) {
	    PatientSearch ps = (PatientSearch) pageRequest;
	    if (ps.getName() != null)
		return repository.findByNameLike(ps.getName(), pageable);
	    else
		return repository.findAll(pageable);
	}
	else
	    return repository.findAll(pageable);
    }
}
