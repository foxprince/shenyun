package cn.anthony.boot.repository;

import cn.anthony.boot.domain.Patient;
import cn.anthony.boot.domain.QPatient;
import com.querydsl.core.BooleanBuilder;
import com.querydsl.core.types.Predicate;
import com.querydsl.core.types.dsl.BooleanExpression;
import com.querydsl.core.types.dsl.DateTimeExpression;
import com.querydsl.core.types.dsl.StringExpression;
import com.querydsl.core.types.dsl.StringPath;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.mongodb.repository.Query;
import org.springframework.data.querydsl.binding.QuerydslBindings;
import org.springframework.data.querydsl.binding.SingleValueBinding;
import org.springframework.util.ObjectUtils;

import java.time.LocalDateTime;
import java.util.*;

public interface PatientRepository
		extends BaseRepository<Patient, QPatient,String> {
	@Override
	default public void customize(QuerydslBindings bindings, QPatient p) {
		bindings.bind(String.class).first(new SingleValueBinding<StringPath, String>() {
			@Override
			public Predicate bind(StringPath path, String value) {
				return path.containsIgnoreCase(value);
			}
		});
		bindings.bind(LocalDateTime.class).all((path, value) -> {
			List<LocalDateTime> l = new ArrayList<LocalDateTime>(value);
			if(value!=null&&value.size()>1)
				return Optional.of(((DateTimeExpression) path).between(l.get(0), l.get(1)));
			else if(value.size()==1)
				return Optional.of(((DateTimeExpression) path).after(l.get(0)));
			return null;
		});
		bindings.bind(String.class).first(( path,  value) -> {
			if(value.contains(",")) {
				String[] values = value.split(",");
				List<String> list = Arrays.asList(values);
				BooleanBuilder builder = new BooleanBuilder();
				for (String s : list) {
					builder.or(((StringExpression) path).containsIgnoreCase(s));
				}
				return builder;
			}
			else if(value.startsWith("!")) {
				return ((StringExpression) path).ne(value.substring(1));
			} else {
				return ((StringExpression) path).containsIgnoreCase(value);
			}
		});
		bindings.bind(p.frontRecords.any().dischargeTime).all((path, value) -> {
			List<Date> l = new java.util.ArrayList<Date>(value);
			if(value!=null&&value.size()>1)
				return Optional.of(((DateTimeExpression) path).between(l.get(0), l.get(1)));
			else if(value.size()==1)
				return Optional.of(((DateTimeExpression) path).after(l.get(0)));
			return null;
		});
		bindings.bind(p.outRecords.any().outDate).all((path, value) -> {
			List<Date> l = new java.util.ArrayList<Date>(value);
			if(value!=null&&value.size()>1)
				return Optional.of(((DateTimeExpression) path).between(l.get(0), l.get(1)));
			else if(value.size()==1)
				return Optional.of(((DateTimeExpression) path).after(l.get(0)));
			return null;
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
