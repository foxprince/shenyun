package cn.anthony.boot.repository;

import org.springframework.data.querydsl.QueryDslPredicateExecutor;
import org.springframework.data.querydsl.binding.QuerydslBinderCustomizer;
import org.springframework.data.querydsl.binding.QuerydslBindings;
import org.springframework.data.repository.PagingAndSortingRepository;

import com.mysema.query.types.path.StringPath;

import cn.anthony.boot.domain.CustomeOption;
import cn.anthony.boot.domain.QCustomeOption;

public interface CustomeOptionRepository
	extends PagingAndSortingRepository<CustomeOption, String>, QueryDslPredicateExecutor<CustomeOption>, QuerydslBinderCustomizer<QCustomeOption> {
    @Override
    default public void customize(QuerydslBindings bindings, QCustomeOption p) {
	bindings.bind(String.class).first((StringPath path, String value) -> path.containsIgnoreCase(value));
    }

}
