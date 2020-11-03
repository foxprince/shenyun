package cn.anthony.boot.service;

import cn.anthony.boot.domain.QSearchModel;
import cn.anthony.boot.domain.SearchModel;
import cn.anthony.boot.repository.SearchModelRepository;
import com.querydsl.core.types.Predicate;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;

@Service
public class SearchModelService extends GenericService<SearchModel, QSearchModel> {
	@Resource
	protected SearchModelRepository repository;

	@Override
	public SearchModelRepository getRepository() {
		return repository;
	}

	@Override
	public Page<SearchModel> find(Predicate predicate, Pageable pageable) {
		return repository.findAll(predicate, pageable);
	}
}
