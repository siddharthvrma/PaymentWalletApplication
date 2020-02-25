package com.capgemini.wallet.test;

import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;
import org.junit.jupiter.api.TestInstance.Lifecycle;

import com.capgemini.wallet.bean.WalletUser;
import com.capgemini.wallet.service.WalletService;
import com.capgemini.wallet.service.WalletServiceImpl;

@TestInstance(Lifecycle.PER_CLASS)
public class Testing {

	WalletUser user;
	WalletService service;
	@BeforeAll
	public void setData() {
		service=new WalletServiceImpl();
		user =new WalletUser();
		user.setUserName("test");
		
	}
	
	@DisplayName("User Valid Testcase")
	@Test
	public void isUserValid() {
	assertEquals(true, user.getUserName().equals("test"));
	}
	@DisplayName("addition test case")
	@Test	
	public void firsttest() {
		assertEquals(2,1+1);
	}
}
