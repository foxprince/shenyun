package cn.anthony.boot.repository;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.repository.PagingAndSortingRepository;

import cn.anthony.boot.domain.Patient;

public interface PatientRepository extends PagingAndSortingRepository<Patient, String> {

    List<Patient> findByPId(String pId);

    // @Query("select from Patient p where p.name like ?1")
    Page<Patient> findByNameLike(String name, Pageable request);
}
