package com.capgemini.wallet.service;

import com.capgemini.wallet.bean.WalletUser;

public interface WalletService {
	
	public void registerUser(WalletUser user);
	
	public String loginAdmin(int adminID,String adminPassword);
	
	public void showNonApprovedAccounts();

	public void showApprovedAccounts();
	
	public void approveAccount(int appAccID);
	
	public void showAccountDetails();
		
	public WalletUser loginUser(int id, String pass);
	
	public void myAccount(WalletUser user);
		
	public void addMoneyToWallet(WalletUser user,double amount);
	
	public void withdrawMoneyFromWallet(WalletUser user,double amt);
	
	public String sendMoney(WalletUser user, int userid,double amnt);
	
	public void showTransacations(WalletUser user);
	
	public void showBalance(WalletUser user);

}
