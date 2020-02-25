package com.capgemini.wallet.dao;

import java.util.HashMap;

import com.capgemini.wallet.bean.WalletUser;

public class WalletDaoImpl implements  WalletDao{
	
	private static HashMap<Integer,WalletUser> userHashMap = new HashMap<Integer,WalletUser>();

	public HashMap<Integer,WalletUser> getUserHashMap() {		
		return userHashMap;
	}

	public void setUserData(WalletUser user) {
		userHashMap.put(user.getUserId(), user);
		
	}

	public void updateUserData(WalletUser user) {	
		userHashMap.replace(user.getUserId(), user);
	}

}
