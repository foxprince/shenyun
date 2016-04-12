package cn.anthony.boot.repository;

import java.util.List;

import org.springframework.data.mongodb.repository.MongoRepository;

import cn.anthony.boot.domain.Customer;

public interface CustomerRepository extends MongoRepository<Customer, String> {

    public Customer findByFirstName(String firstName);

    public List<Customer> findByLastName(String lastName);

}
