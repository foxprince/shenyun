package cn.anthony.boot.repository;

import org.springframework.data.querydsl.QueryDslPredicateExecutor;
import org.springframework.data.querydsl.binding.QuerydslBinderCustomizer;
import org.springframework.data.querydsl.binding.QuerydslBindings;
import org.springframework.data.repository.PagingAndSortingRepository;

import com.mysema.query.types.path.StringPath;

import cn.anthony.boot.domain.QSearchModel;
import cn.anthony.boot.domain.SearchModel;

public interface SearchModelRepository
	extends PagingAndSortingRepository<SearchModel, String>, QueryDslPredicateExecutor<SearchModel>, QuerydslBinderCustomizer<QSearchModel> {
    @Override
    default public void customize(QuerydslBindings bindings, QSearchModel p) {
	bindings.bind(String.class).first((StringPath path, String value) -> path.containsIgnoreCase(value));
    }

}
