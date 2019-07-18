package cn.anthony.boot.service;

import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.data.repository.PagingAndSortingRepository;

import cn.anthony.boot.exception.EntityNotFound;

public abstract class GenericService<T> {
	public Logger logger = LoggerFactory.getLogger(this.getClass());

	public abstract PagingAndSortingRepository<T, String> getRepository();

	public T create(T item) {
		return getRepository().save(item);
	}

	public T update(T item) throws EntityNotFound {
		return getRepository().save(item);
	}

	public T findById(String id) {
		return getRepository().findOne(id);
	}

	public T delete(String id) throws EntityNotFound {
		T deletedT = getRepository().findOne(id);
		if (deletedT == null)
			throw new EntityNotFound(getClassName().toString());
		getRepository().delete(deletedT);
		return deletedT;
	}

	public Iterable<T> findAll() {
		return getRepository().findAll();
	}

	public Page<T> findAll(int page, int size) {
		return getRepository().findAll(new PageRequest(page - 1, size, Sort.Direction.DESC, "id"));
	}

	public Type getClassName() {
		return (((ParameterizedType) getClass().getGenericSuperclass()).getActualTypeArguments()[0]);
	}
	/**
	 * 按照查询条件分页显示
	 * 
	 * @param pageRequest
	 * @param relate
	 * @return
	 */
	// public abstract Page<T> findPage(cn.anthony.boot.web.PageRequest
	// pageRequest);
}
