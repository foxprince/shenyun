package cn.anthony.boot.service;

import cn.anthony.boot.domain.DepartmentConfig;
import cn.anthony.boot.domain.QDepartmentConfig;
import cn.anthony.boot.repository.DepartmentConfigRepository;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;

@Service
public class DepartmentConfigService extends GenericService<DepartmentConfig, QDepartmentConfig>{
	@Resource
	protected DepartmentConfigRepository repository;
	@Override
	public DepartmentConfigRepository getRepository() {
		return repository;
	}
}
