package com.uplus.msa.service;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import org.modelmapper.ModelMapper;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.uplus.msa.dto.CustomerDTO;
import com.uplus.msa.entity.Customer;
import com.uplus.msa.repository.CustomerRepository;
import com.uplus.msa.util.AppUtils;

import lombok.RequiredArgsConstructor;

@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class CustomerServiceImpl implements CustomerService {
	
	private final CustomerRepository repository;
	private final ModelMapper modelMapper;
	
	//constructor injection
//	public CustomerServiceImpl(CustomerRepository repository) {
//		this.repository = repository;
//	}

	@Override
	public List<CustomerDTO> getAllCustomers() throws Exception {
		List<Customer> customerList = repository.findAll();
		//1. 직접매핑하기
//		List<CustomerDTO> dtoList = new ArrayList<>();
//		for (Customer customer : customerList) {
//			CustomerDTO customerDTO = CustomerDTO.builder()
//				.id(customer.getId())
//				.name(customer.getName())
//				.address(customer.getAddress())
//			.build();
//			dtoList.add(customerDTO);
//		}
		
		//2.Stream과 BeanUtils 사용하여 매핑하기
		List<CustomerDTO> dtoList = customerList.stream() //Stream<Customer>
					.map(cust -> AppUtils.entityToDto(cust)) //Stream<CustomerDTO>
					.collect(Collectors.toList());
		return dtoList;
	}

	@Override
	public CustomerDTO getCustomerById(Long id) throws Exception {
		Customer customer = repository.findById(id).orElseGet(() -> new Customer());
		//1.직접매핑하기
//		CustomerDTO customerDTO = CustomerDTO.builder()
//					.id(customer.getId())
//					.name(customer.getName())
//					.address(customer.getAddress())
//					.build();		
		
		//2.BeanUtils의 copyProperties() 사용
//		CustomerDTO customerDTO = new CustomerDTO();
//		BeanUtils.copyProperties(customer, customerDTO);
		
//		CustomerDTO customerDTO = AppUtils.entityToDto(customer);
		
		//3. ModelMapper(외부라이브러리)의 map() 사용
		CustomerDTO customerDTO = modelMapper.map(customer, CustomerDTO.class);
		
		return customerDTO;
	}

	
}
