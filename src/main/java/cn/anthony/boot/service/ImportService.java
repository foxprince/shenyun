package cn.anthony.boot.service;

import cn.anthony.boot.domain.ImportModel;
import cn.anthony.boot.domain.QImportModel;
import cn.anthony.boot.repository.ImportModelRepository;
import com.querydsl.core.types.Predicate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

@Service
public class ImportService extends GenericService<ImportModel, QImportModel> {
	@Autowired
	private ImportModelRepository repository;

	@Override
	public ImportModelRepository getRepository() {
		return repository;
	}

	@Override
	public Page<ImportModel> find(Predicate predicate, Pageable pageable) {
		return repository.findAll(predicate, pageable);
	}

	public Page<ImportModel> find(Pageable pageable) {
		return repository.findAll(pageable);
	}
}
