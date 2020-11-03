package cn.anthony.boot.service;

import cn.anthony.boot.exception.EntityNotFound;
import cn.anthony.boot.repository.BaseRepository;
import com.querydsl.core.types.EntityPath;
import com.querydsl.core.types.Predicate;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;

import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;
import java.util.Optional;

public abstract class GenericService<T,QT extends EntityPath<T>> {
	public Logger logger = LoggerFactory.getLogger(this.getClass());

	public abstract BaseRepository<T, QT, String> getRepository();

	public T create(T item) {
		return getRepository().save(item);
	}

	public T update(T item) throws EntityNotFound {
		return getRepository().save(item);
	}

	public T findById(String id) {
		return getRepository().findById(id).get();
	}

	public T delete(String id) throws EntityNotFound {
		Optional<T> deletedT = getRepository().findById(id);
		if (deletedT == null) {
			throw new EntityNotFound(getClassName().toString());
		}
		getRepository().delete(deletedT.get());
		return deletedT.get();
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

	public Page<T> find(Predicate predicate, Pageable pageable) {
		return getRepository().findAll(predicate, pageable);
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
