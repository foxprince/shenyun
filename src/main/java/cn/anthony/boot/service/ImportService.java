package cn.anthony.boot.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.stereotype.Service;

import com.querydsl.core.types.Predicate;

import cn.anthony.boot.domain.ImportModel;
import cn.anthony.boot.repository.ImportModelRepository;

@Service
public class ImportService extends GenericService<ImportModel> {
	@Autowired
	private ImportModelRepository repository;

	@Override
	public PagingAndSortingRepository<ImportModel, String> getRepository() {
		return repository;
	}

	public Page<ImportModel> find(Predicate predicate, Pageable pageable) {
		return repository.findAll(predicate, pageable);
	}

	public Page<ImportModel> find(Pageable pageable) {
		return repository.findAll(pageable);
	}
}
