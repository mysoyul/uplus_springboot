package com.uplus.msa.util;

import org.springframework.beans.BeanUtils;

import com.uplus.msa.dto.CustomerDTO;
import com.uplus.msa.entity.Customer;

public class AppUtils {
	public static CustomerDTO entityToDto(Customer entity) {
		CustomerDTO dto = new CustomerDTO();
		BeanUtils.copyProperties(entity, dto);
		return dto;
	}
	
	public static Customer dtoToEntity(CustomerDTO dto) {
		Customer customer = new Customer();
		BeanUtils.copyProperties(dto, customer);
		return customer;
	}
}
