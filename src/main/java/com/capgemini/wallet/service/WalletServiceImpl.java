package com.capgemini.wallet.service;

import java.time.LocalDateTime;
import java.util.HashMap;

import com.capgemini.wallet.bean.WalletAccount.Status;
import com.capgemini.wallet.bean.WalletTransaction;
import com.capgemini.wallet.bean.WalletUser;
import com.capgemini.wallet.dao.WalletDaoImpl;

public class WalletServiceImpl implements WalletService {

	int adminID = 101;
	String adminPassword = "password";
	WalletDaoImpl walletDaoImpl=new WalletDaoImpl();
	
	//Function To Register Users
	
	public void registerUser(WalletUser user) {

		walletDaoImpl.setUserData(user);
		System.out.println("\nYour Account Has Been Successfully Registered");
		System.out.println("Your Account ID : " + user.getwAccount().getAccountId() + "\n");

	}

	//Function To Login As Admin

	public String loginAdmin(int aid, String apass) {
		if (aid == adminID && apass.equals(adminPassword))
			return "Logged In";
		else
			return "Not Logged In";
	}

	//Function To Show Accounts That Have Not Been Approved By Admin
	
	public void showNonApprovedAccounts() {
		System.out.println("Account ID \t\tUser ID \t\tLogin Name");
		HashMap<Integer,WalletUser> uHashMap=walletDaoImpl.getUserHashMap();
		uHashMap.forEach((k,v)->{
			if (uHashMap.get(k).getwAccount().getType().equals(Status.NotApproved)) {
				System.out.println(uHashMap.get(k).getwAccount().getAccountId() + "\t\t\t" +uHashMap.get(k).getUserId() + "\t\t\t"
						+ uHashMap.get(k).getLoginName());
			}
		});
		
	}

	//Function To Show Accounts That Have Been Approved By Admin
	
	public void showApprovedAccounts() {
		System.out.println("Account ID \t\tUser ID \t\tLogin Name");
		HashMap<Integer,WalletUser> uHashMap=walletDaoImpl.getUserHashMap();
		uHashMap.forEach((k,v)->{
			if (uHashMap.get(k).getwAccount().getType().equals(Status.Approved)) {
				System.out.println(uHashMap.get(k).getwAccount().getAccountId() + "\t\t\t" +uHashMap.get(k).getUserId() + "\t\t\t"
						+ uHashMap.get(k).getLoginName());
			}
		});
	}

	//Function That Approves Accounts By Admin
	
	public void approveAccount(int appAccID) {
		HashMap<Integer,WalletUser> uHashMap=walletDaoImpl.getUserHashMap();
		uHashMap.forEach((k,v)->{
			if (uHashMap.get(k).getwAccount().getAccountId() == appAccID) {
				uHashMap.get(k).getwAccount().setType(Status.Approved);
			}
		});

		System.out.println(appAccID+" Has Been Approved");
	}

	//Function That Show Details of Accounts to an Admin
	
	public void showAccountDetails() {
		System.out.println("Account ID \t\tUser ID \t\tUserName \t\tLogin Name \t\tStatus \t\t\tAccount Balance");
		HashMap<Integer,WalletUser> uHashMap=walletDaoImpl.getUserHashMap();
		uHashMap.forEach((k,v)->
				System.out.println(uHashMap.get(k).getwAccount().getAccountId() + "\t\t\t" + uHashMap.get(k).getUserId() + "\t\t\t"
						+ uHashMap.get(k).getUserName() + "\t\t\t" + uHashMap.get(k).getLoginName() + "\t\t\t" + uHashMap.get(k).getwAccount().getType()+"\t\t"+uHashMap.get(k).getwAccount().getAccountBalance())
		);		
	}

	//Function That Checks Whether a User can Login or Not
	
	public WalletUser loginUser(int id, String pass) {
		HashMap<Integer,WalletUser> uHashMap=walletDaoImpl.getUserHashMap();

		boolean flag = false;
		WalletUser currentUser = null;
		if(uHashMap.containsKey(id) && uHashMap.get(id).getUserId()==id && uHashMap.get(id).getPassword().equals(pass)) {

				if (uHashMap.get(id).getwAccount().getType().equals(Status.Approved)) {
					flag = true;
					currentUser = uHashMap.get(id);
					
				} else {
					System.out.println("\nAccount Not Approved By Admin\n");
					return null;

				}

			}


		if (flag) {

			return currentUser;
		} else {
			
			System.out.println("\nInvalid UserName & Password\n");
			return null;
		}

	}

	//Function That Shows Account Details of a Logged In User
	
	public void myAccount(WalletUser user) {
		System.out.println("\nUser \t\t Account ID \t\t Account Balance");
		System.out.println(user.getLoginName()+" \t\t " +user.getwAccount().getAccountId()+" \t\t\t "+user.getwAccount().getAccountBalance());

	}

	//Function That Shows Account Details of a Logged In User
	
	public void showBalance(WalletUser user) {

		System.out.println("Current Account Balance\t\t:\t" + user.getwAccount().getAccountBalance());
	}

	//Function That Allows a Logged In User to Add Amount in his Wallet
	
	public void addMoneyToWallet(WalletUser user, double amount) {
		user.getwAccount().setAccountBalance(user.getwAccount().getAccountBalance() + amount);
		System.out.println(amount + " Added Successfully\n");
		int tid = (int) (Math.random() * 1234 + 9999);
		LocalDateTime date = LocalDateTime.now();
		String description = "Deposit";
		user.setwTransaction(new WalletTransaction(tid, description, date, amount, user.getwAccount().getAccountBalance()));
		user.getwAccount().getTransactionHistory().add(user.getwTransaction());

	}
	
	//Function That Allows a Logged In User to Withdraw Amount from his Wallet
	
	public void withdrawMoneyFromWallet(WalletUser user, double amt) {
		if(user.getwAccount().getAccountBalance()>amt)
		{
			user.getwAccount().setAccountBalance(user.getwAccount().getAccountBalance()-amt);
			System.out.println(amt + " Withdrawn Successfully\n");
			int tid = (int) (Math.random() * 1234 + 9999);
			LocalDateTime date = LocalDateTime.now();
			String description = "Withdraw";
			user.setwTransaction(new WalletTransaction(tid, description, date, amt, user.getwAccount().getAccountBalance()));
			user.getwAccount().getTransactionHistory().add(user.getwTransaction());

		}
		else
			System.out.println("Cannot Withdraw "+amt+"from your account as your Current Balance is "+user.getwAccount().getAccountBalance()+"\n");
	}
	
	//Function That Allows a Logged In User to Send Money to any User

	public String sendMoney(WalletUser user, int userid,double amnt) {		
		HashMap<Integer,WalletUser> uHashMap=walletDaoImpl.getUserHashMap();
		String msg="";
		int i=0;
		if(uHashMap.containsKey(userid)) {

				if(uHashMap.get(userid).getwAccount().getType().equals(Status.Approved))
				{
					if(user.getwAccount().getAccountBalance()>amnt) {
						user.getwAccount().setAccountBalance(user.getwAccount().getAccountBalance()-amnt);
						uHashMap.get(userid).getwAccount().setAccountBalance(uHashMap.get(userid).getwAccount().getAccountBalance()+amnt);
						msg=amnt+" Transferred To "+uHashMap.get(userid).getUserName()+" (Account ID : "+uHashMap.get(userid).getwAccount().getAccountId()+" )\n";
						i=1;					
					}
					else if(user.getwAccount().getAccountBalance()<amnt) {
						msg="\nTransaction Failed\nCannot Transfer "+amnt+" as your current balance is "+user.getwAccount().getAccountBalance()+"\n";					    
						return  msg;
					}
					
				}
				else if(uHashMap.get(userid).getwAccount().getType().equals(Status.NotApproved)) {
					msg="\nTransaction Failed\nCannot Transfer as that Account is not Approved by Admin \n";					
					return msg;
					
				}

		}				
		if(i==1) {
			int tidCurrentUser=(int) (Math.random()*1234 + 9999);
			int tidSendUser=(int) (Math.random()*1234 + 9999);
			LocalDateTime date=LocalDateTime.now();
			String descriptionCurrentUser = "Money Transfer";
			String descriptionSendUser = "Money Received";
			user.setwTransaction(new WalletTransaction(tidCurrentUser, descriptionCurrentUser, date, amnt, user.getwAccount().getAccountBalance()));
			user.getwAccount().getTransactionHistory().add(user.getwTransaction());
			uHashMap.get(userid).setwTransaction(new WalletTransaction(tidSendUser, descriptionSendUser, date, amnt, uHashMap.get(userid).getwAccount().getAccountBalance()));
			uHashMap.get(userid).getwAccount().getTransactionHistory().add(uHashMap.get(userid).getwTransaction());
			return msg;
			}
		else	
			return  "Transaction Failed\nInvalid User ID\n";
		}
							
	//Function That Allows a Logged In User to Show History of all Transactions
	
	public void showTransacations(WalletUser user) {
		for (WalletTransaction i : user.getwAccount().getTransactionHistory()) {
			if (i.getTransactionId() != null) {
				System.out.println("Transaction ID        \t\t: \t" + i.getTransactionId());
				System.out.println("Type Of Transaction   \t\t: \t" + i.getDescription());
				System.out.println("Date Of Transaction   \t\t: \t" + i.getDateOfTransaction());
				System.out.println("Amount Transacted     \t\t: \t" + i.getAmount());
				System.out.println("Account Balance\t\t       : \t" + i.getAccountBalance());
				System.out.println("\n");
			}
		}
	}


}
