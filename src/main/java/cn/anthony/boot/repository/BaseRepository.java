package cn.anthony.boot.repository;

import com.querydsl.core.types.EntityPath;
import com.querydsl.core.types.dsl.SimpleExpression;
import com.querydsl.core.types.dsl.StringExpression;
import org.springframework.data.querydsl.QuerydslPredicateExecutor;
import org.springframework.data.querydsl.binding.QuerydslBinderCustomizer;
import org.springframework.data.querydsl.binding.QuerydslBindings;
import org.springframework.data.repository.NoRepositoryBean;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.web.bind.annotation.CrossOrigin;

import java.io.Serializable;
import java.util.Arrays;
import java.util.List;

@NoRepositoryBean
@CrossOrigin
public interface BaseRepository<T, QT extends EntityPath<T>, ID extends Serializable>
		extends PagingAndSortingRepository<T, ID>, QuerydslPredicateExecutor<T>, QuerydslBinderCustomizer<QT> {
	@Override
	default public void customize(QuerydslBindings bindings, QT p) {
		//bindings.bind(String.class).first((StringPath path, String value) -> path.containsIgnoreCase(value));
		//bindings.bind(String.class).first((StringPath path, String value) -> path.equalsIgnoreCase(value));
		bindings.bind(String.class).first(( path,  value) -> {
			if(value.contains(",")) {
				String[] values = value.split(",");
				List<String> list = Arrays.asList(values);
		        return  ((SimpleExpression<String>) path).in(list);
			}
			return ((StringExpression) path).equalsIgnoreCase(value);
		});
	}
}