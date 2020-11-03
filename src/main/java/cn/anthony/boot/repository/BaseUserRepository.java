package cn.anthony.boot.repository;

import cn.anthony.boot.domain.BaseUser;
import org.springframework.data.repository.PagingAndSortingRepository;

public interface BaseUserRepository extends PagingAndSortingRepository<BaseUser, Long> {
	public BaseUser findByEmail(String email);
}
