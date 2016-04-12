package cn.anthony.boot.repository;

import org.springframework.data.repository.PagingAndSortingRepository;

import cn.anthony.boot.domain.Patient;

public interface PatientRepository extends PagingAndSortingRepository<Patient, String> {

}
