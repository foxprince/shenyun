package cn.anthony.boot.repository;

import org.springframework.data.querydsl.QueryDslPredicateExecutor;
import org.springframework.data.querydsl.binding.QuerydslBinderCustomizer;
import org.springframework.data.querydsl.binding.QuerydslBindings;
import org.springframework.data.repository.PagingAndSortingRepository;

import com.mysema.query.types.path.StringPath;

import cn.anthony.boot.domain.ImportModel;
import cn.anthony.boot.domain.QImportModel;

public interface ImportModelRepository extends PagingAndSortingRepository<ImportModel, String>, QueryDslPredicateExecutor<ImportModel>,
		QuerydslBinderCustomizer<QImportModel> {
	@Override
	default public void customize(QuerydslBindings bindings, QImportModel p) {
		bindings.bind(String.class).first((StringPath path, String value) -> path.containsIgnoreCase(value));
	}
}
