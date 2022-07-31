package com.subhan.onlinebank.repository;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import com.subhan.onlinebank.entiry.Customer;

@Repository
public interface CustomerRepository extends CrudRepository<Customer, String>{
	Boolean existsByEmail(String email);
	Boolean existsByPhone(String phone);
}
