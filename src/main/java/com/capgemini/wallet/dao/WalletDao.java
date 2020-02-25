package com.capgemini.wallet.dao;

import java.util.HashMap;

import com.capgemini.wallet.bean.WalletUser;

public interface WalletDao {
 HashMap<Integer,WalletUser> getUserHashMap();
 public void setUserData(WalletUser user);
 public void updateUserData(WalletUser user);

}
