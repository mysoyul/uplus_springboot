package com.uplus.msa.service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import org.modelmapper.ModelMapper;
import org.springframework.beans.BeanUtils;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
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
/*		
		List<CustomerDTO> dtoList = customerList.stream() //Stream<Customer>
					//.map(cust -> AppUtils.entityToDto(cust)) //Stream<CustomerDTO>
					.map(AppUtils::entityToDto)
					.collect(Collectors.toList());
*/					
		//3.Stream과 ModelMapper 사용하여 매핑하기
		List<CustomerDTO> dtoList = customerList.stream() //Stream<Customer>
					.map(cust -> modelMapper.map(cust, CustomerDTO.class)) //Stream<CustomerDTO>
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
	
	
	@Override
	public ResponseEntity<?> getCustomerByIdRE(Long id) throws Exception {
		Optional<Customer> optional = repository.findById(id);
		//id와 매핑되는 Customer가 없다면 404
		if (!optional.isPresent()) {
			//return ResponseEntity.notFound().build();
			return new ResponseEntity<>(HttpStatus.NOT_FOUND);
		}
		Customer customer = optional.get();
		CustomerDTO customerDTO = modelMapper.map(customer, CustomerDTO.class);
		return ResponseEntity.ok(customerDTO);
	}
	
	@Override
	public List<CustomerDTO> getCustomerByIdList(List<Long> ids) throws Exception {
		return repository.findByIdIn(ids) //List<Customer>
						.stream()	//Stream<Customer>
						.map(cust -> modelMapper.map(cust, CustomerDTO.class)) //Stream<CustomerDTO>
						.collect(Collectors.toList()); //List<CustomerDTO>						
	}
	
	
	@Override
	public List<CustomerDTO> getCustomersPaing(Pageable pageable) throws Exception {
		Page<Customer> customerPage = repository.findAll(pageable);
		return customerPage.get() //Stream<Customer>
						   .map(cust -> modelMapper.map(cust, CustomerDTO.class)) //Stream<CustomerDTO>
						   .collect(Collectors.toList());						   
	}

	@Transactional
	@Override
	public Long createCustomer(CustomerDTO custDTO) throws Exception {
		//CustomerDTO -> Customer 매핑
		Customer entity = modelMapper.map(custDTO, Customer.class);
		Customer savedCustomer = repository.save(entity);
		return savedCustomer.getId();
	}
	
	@Override
	@Transactional
	public ResponseEntity<?> updateCustomer(Long id, CustomerDTO dto) throws Exception {
		//id로 조회
		Optional<Customer> optional = repository.findById(id);
		//없으면 404
		if(!optional.isPresent()) {
			return ResponseEntity.status(HttpStatus.NOT_FOUND).body(id + " customer not found");
		}
		//있으면 입력받은 수정데이터를 엔티티로 매핑
		Customer customer = optional.get();
		customer.setName(dto.getName());
		customer.setAddress(dto.getAddress());
		//수정처리
		Customer updatedCustomer = repository.save(customer);
		
		//수정된 엔티티를 DTO로 매핑
		CustomerDTO customerDTO = modelMapper.map(updatedCustomer, CustomerDTO.class);
		return ResponseEntity.ok(customerDTO);
	}
}
