package cn.anthony.boot.service;

import javax.annotation.Resource;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import com.mysema.query.types.Predicate;

import cn.anthony.boot.domain.SearchModel;
import cn.anthony.boot.repository.SearchModelRepository;

@Service
public class SearchModelService extends GenericService<SearchModel> {
    @Resource
    protected SearchModelRepository repository;

    public SearchModelRepository getRepository() {
	return repository;
    }

    public Page<SearchModel> find(Predicate predicate, Pageable pageable) {
	return repository.findAll(predicate, pageable);
    }
}
