package cn.anthony.boot.repository;

import java.util.Collection;
import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.querydsl.QueryDslPredicateExecutor;
import org.springframework.data.querydsl.binding.MultiValueBinding;
import org.springframework.data.querydsl.binding.QuerydslBinderCustomizer;
import org.springframework.data.querydsl.binding.QuerydslBindings;
import org.springframework.data.repository.PagingAndSortingRepository;

import com.mysema.query.types.Predicate;
import com.mysema.query.types.path.NumberPath;
import com.mysema.query.types.path.StringPath;

import cn.anthony.boot.domain.Patient;
import cn.anthony.boot.domain.QPatient;

public interface PatientRepository
	extends PagingAndSortingRepository<Patient, String>, QueryDslPredicateExecutor<Patient>, QuerydslBinderCustomizer<QPatient> {
    @Override
    default public void customize(QuerydslBindings bindings, QPatient p) {
	// bindings.bind(p.name).first((path, value) -> path.contains(value));
	bindings.bind(p.age).all(new MultiValueBinding<NumberPath<Integer>, Integer>() {
	    @Override
	    public Predicate bind(NumberPath<Integer> path, Collection<? extends Integer> value) {
		return null;// path.between(p.minAge, p.maxAge);
	    }
	});
	bindings.bind(String.class).first((StringPath path, String value) -> path.containsIgnoreCase(value));
    }

    List<Patient> findByPId(String pId);

    // @Query("select from Patient p where p.name like ?1")
    Page<Patient> findByNameLike(String name, Pageable request);



}
