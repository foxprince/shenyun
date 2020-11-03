package cn.anthony.boot.service;

import cn.anthony.boot.domain.CustomeOption;
import cn.anthony.boot.domain.QCustomeOption;
import cn.anthony.boot.repository.CustomeOptionRepository;
import com.querydsl.core.types.Predicate;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;

@Service
public class CustomeOptionService extends GenericService<CustomeOption, QCustomeOption> {
	@Resource
	protected CustomeOptionRepository repository;

	@Override
	public CustomeOptionRepository getRepository() {
		return repository;
	}

	@Override
	public Page<CustomeOption> find(Predicate predicate, Pageable pageable) {
		return repository.findAll(predicate, pageable);
	}
}
