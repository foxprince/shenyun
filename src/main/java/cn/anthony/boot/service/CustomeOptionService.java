package cn.anthony.boot.service;

import javax.annotation.Resource;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import com.mysema.query.types.Predicate;

import cn.anthony.boot.domain.CustomeOption;
import cn.anthony.boot.repository.CustomeOptionRepository;

@Service
public class CustomeOptionService extends GenericService<CustomeOption> {
	@Resource
	protected CustomeOptionRepository repository;

	@Override
	public CustomeOptionRepository getRepository() {
		return repository;
	}

	public Page<CustomeOption> find(Predicate predicate, Pageable pageable) {
		return repository.findAll(predicate, pageable);
	}
}
