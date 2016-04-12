package cn.anthony.boot.repository;

import org.springframework.data.repository.PagingAndSortingRepository;

import cn.anthony.boot.domain.User;

public interface UserRepository extends PagingAndSortingRepository<User, Long> {
    public User findByEmail(String email);
}
