package cn.anthony.boot.repository;

import org.springframework.data.repository.PagingAndSortingRepository;

import cn.anthony.boot.domain.BaseUser;

public interface BaseUserRepository extends PagingAndSortingRepository<BaseUser, Long> {
    public BaseUser findByEmail(String email);
}
