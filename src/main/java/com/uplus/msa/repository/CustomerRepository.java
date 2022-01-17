package com.uplus.msa.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.uplus.msa.entity.Customer;

public interface CustomerRepository extends JpaRepository<Customer, Long>{
	Optional<Customer> findByName(String name);
}
