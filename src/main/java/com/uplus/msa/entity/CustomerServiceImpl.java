package com.uplus.msa.entity;

import java.util.List;


import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.uplus.msa.dto.CustomerDTO;
import com.uplus.msa.repository.CustomerRepository;
import com.uplus.msa.service.CustomerService;

import lombok.RequiredArgsConstructor;

@Service
@Transactional
@RequiredArgsConstructor
public class CustomerServiceImpl implements CustomerService {
	
	private final CustomerRepository repository;
	
	//constructor injection
//	public CustomerServiceImpl(CustomerRepository repository) {
//		this.repository = repository;
//	}

	@Override
	public List<CustomerDTO> getAllCustomers() throws Exception {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public CustomerDTO getCustomerById(Long id) throws Exception {
		// TODO Auto-generated method stub
		return null;
	}

}
