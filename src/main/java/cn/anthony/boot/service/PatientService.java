package cn.anthony.boot.service;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import cn.anthony.boot.domain.Patient;
import cn.anthony.boot.repository.PatientRepository;

@Service
public class PatientService extends GenericService<Patient> {
    @Resource
    protected PatientRepository repository;

    public PatientRepository getRepository() {
	return repository;
    }

}
