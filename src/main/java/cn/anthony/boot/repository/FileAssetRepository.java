package cn.anthony.boot.repository;

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

import cn.anthony.boot.domain.FileAsset;
import cn.anthony.boot.domain.QFileAsset;

public interface FileAssetRepository
		extends PagingAndSortingRepository<FileAsset, String>, QueryDslPredicateExecutor<FileAsset>, QuerydslBinderCustomizer<QFileAsset> {
	@Override
	default public void customize(QuerydslBindings bindings, QFileAsset p) {
		bindings.bind(String.class).first(new SingleValueBinding<StringPath, String>() {
			@Override
			public Predicate bind(StringPath path, String value) {
				return path.containsIgnoreCase(value);
			}
		});
	}

	@Query("{ $or : [ { $where: '?0 == null' } , { field : ?0 } ] }")
	List<FileAsset> findAll(String query);

	@Query("{ $or : [ { $where: '?0.length == 0' } , { field : { $in : ?0 } } ] }")
	List<FileAsset> findAllIn(String query, Pageable pageable);
	
	@Query("{nr:{$ne:null}}")
	Page<FileAsset> findWithAsset(Pageable pageable);

	List<FileAsset> findByNr(String nr);

}
