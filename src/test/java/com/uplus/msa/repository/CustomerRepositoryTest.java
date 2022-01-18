package com.uplus.msa.repository;

import java.util.List;
import java.util.Optional;
import java.util.function.Consumer;
//static import 
import static java.util.stream.Collectors.toList;

import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import com.uplus.msa.entity.Customer;

@SpringBootTest
public class CustomerRepositoryTest {

	@Autowired
	private CustomerRepository repository;
	
	@Test @Disabled
	public void customer() {
		Customer customer = new Customer();
		customer.setName("유플러스2");
		customer.setAddress("서울 상암2");
		
		Customer savedCustomer = repository.save(customer);
		System.out.println("Saved Customer " + savedCustomer);
		
		Customer customer2 = new Customer();
		customer2.setName("엘지");
		customer2.setAddress("서울 상암3");
		repository.save(customer2);
	}
	
	@Test //@Disabled
	public void customer_finder() {
		Optional<Customer> optional = repository.findById(20L);
		
		if(optional.isPresent()) {
			Customer customer = optional.get();
			System.out.println(customer);
		}
		
		Customer customer2 = optional.orElse(new Customer());
		System.out.println(customer2);
		
		//orElseGet(Supplier) T get()
		customer2 = optional.orElseGet(() -> new Customer());
		
		//orElseThrow
		customer2 = optional.orElseThrow(() -> new RuntimeException("Customer Not Found"));
		System.out.println(customer2);
		
		Customer customer3 = repository.findByName("유플러스").orElse(new Customer());
		System.out.println(customer3);
		
		List<Customer> custList = repository.findAll();
		//익명내부클래스 
		custList.forEach(new Consumer<Customer>() {
			@Override
			public void accept(Customer t) {
				System.out.println(t);				
			}
		});
		
		//Lambda expression
		custList.forEach(cust -> System.out.println(">>>" + cust));
		
		//Method Reference
		custList.forEach(System.out::println);		
	}
	
	@Test @Disabled
	public void name_list() {
		//List<Customer> --> List<String> name 리스트
		List<String> nameList = repository.findAll() //List<Customer>
				  .stream()  //Stream<Customer>
				  //map(Function)  R apply(T t)
				  .map(cust -> cust.getName())  //Stream<String>
				  .collect(toList()); //List<String>
		
		nameList.forEach(System.out::println);
	}
	
	
	@Test @Disabled
	public void addr_list() {
		//id값이 3보다 큰 Customer들의 주소 리스트를 출력
		List<String> addrList = repository.findAll()  //List<Customer>
				  .stream()	  //Stream<Customer>
				  //filter(Predicate) boolean test(T t)
				  .filter(cust -> cust.getId() >= 3L) //Stream<Customer>
				  //map(Function)  R apply(T t)
				  //.map(cust -> cust.getAddress())
				  .map(Customer::getAddress) //Stream<String>
				  .collect(toList());	//List<String>
		
		addrList.forEach(System.out::println);
	}
	
	
}
