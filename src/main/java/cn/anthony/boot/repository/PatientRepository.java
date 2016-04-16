package cn.anthony.boot.repository;

import java.util.List;

import org.springframework.data.repository.PagingAndSortingRepository;

import cn.anthony.boot.domain.Patient;

public interface PatientRepository extends PagingAndSortingRepository<Patient, String> {

    List<Patient> findByPId(String pId);

}
