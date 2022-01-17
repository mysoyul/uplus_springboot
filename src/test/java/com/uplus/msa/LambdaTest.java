package com.uplus.msa;

import org.junit.jupiter.api.Test;

public class LambdaTest {

	@Test
	public void innerclass() {
		Thread t1 = new Thread(new Runnable() {			
			@Override
			public void run() {
				System.out.println("익명내부클래스로 구현");				
			}
		});
		t1.start();
	}
	
	@Test
	public void lambda() {
		Thread t2 = new Thread(() -> System.out.println("람다식으로 구현"));
		t2.start();
	}
	
		
	
}
