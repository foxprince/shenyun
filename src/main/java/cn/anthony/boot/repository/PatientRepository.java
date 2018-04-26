package cn.anthony.boot.repository;

import java.util.Date;
import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.mongodb.repository.Query;
import org.springframework.data.querydsl.QueryDslPredicateExecutor;
import org.springframework.data.querydsl.binding.QuerydslBinderCustomizer;
import org.springframework.data.querydsl.binding.QuerydslBindings;
import org.springframework.data.querydsl.binding.SingleValueBinding;
import org.springframework.data.repository.PagingAndSortingRepository;

import com.querydsl.core.types.Predicate;
import com.querydsl.core.types.dsl.StringPath;

import cn.anthony.boot.domain.Patient;
import cn.anthony.boot.domain.QPatient;

public interface PatientRepository
		extends PagingAndSortingRepository<Patient, String>, QueryDslPredicateExecutor<Patient>, QuerydslBinderCustomizer<QPatient> {
	@Override
	default public void customize(QuerydslBindings bindings, QPatient p) {
		bindings.bind(String.class).first(new SingleValueBinding<StringPath, String>() {
			@Override
			public Predicate bind(StringPath path, String value) {
				return path.containsIgnoreCase(value);
			}
		});
	}

	Patient findByPId(String pId);

	// @Query("select from Patient p where p.name like ?1")
	Page<Patient> findByNameLike(String name, Pageable request);
	List<Patient> findBySrcFileLike(String srcFile);

	@Query("{ $or : [ { $where: '?0 == null' } , { field : ?0 } ] }")
	List<Patient> findAll(String query);

	@Query("{ $or : [ { $where: '?0.length == 0' } , { field : { $in : ?0 } } ] }")
	List<Patient> findAllIn(String query, Pageable pageable);
	
	@Query("{assets:{$ne:null}}")
	Page<Patient> findWithAsset(Pageable pageable);
	
	List<Patient> findBySource(String source);

	List<Patient> findBySourceAndFrontRecordsAdmissionTimeBetween(String source,Date begin,Date end);
	
}
