package com.capgemini.wallet.ui;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.List;

import com.capgemini.wallet.bean.WalletUser;
import com.capgemini.wallet.service.WalletServiceImpl;

import com.capgemini.wallet.exception.*;

public class UserInterface {
	InputStreamReader isr;
	BufferedReader bsr;
	List<WalletUser> userList;
	WalletServiceImpl wCon;
	String exceptionOccured="Exception Occured : ";
	
	public static void main(String[] args) {

		UserInterface ui = new UserInterface();
		ui.isr = new InputStreamReader(System.in);
		ui.bsr = new BufferedReader(ui.isr);
		ui.wCon=new WalletServiceImpl();
		ui.paymentWallet();
	}

	public void paymentWallet(){
			
			try {
								
				System.out.println("Welcome to Payment Wallet\t\t\t\t\t \n  1. Login \n  2. Register  \n  3. Exit\n");
				int choice = Integer.parseInt(bsr.readLine());
				switch (choice) {				
				case 1:				
					login();
					break;					
				case 2:
					registerUser();
					break;
				case 3:
					System.out.println("\n THANKYOU FOR USING OUR APP");
					System.exit(0);
					break;
				default:
					throw new WalletException("Wrong Choice Entered\n");

				}
					
			}
			catch(Exception e) {
				System.out.println(exceptionOccured+e.getMessage());
//				paymentWallet();
			}
			
	}

	private void registerUser() {
		
		try {
			System.out.print("Enter your userId      : ");
			Integer userId = Integer.parseInt(bsr.readLine());
			System.out.print("Enter your userName    : ");
			String userName = bsr.readLine();
			System.out.print("Enter your password    : ");
			String password = bsr.readLine();
			System.out.print("Enter your phoneNumber : ");
			String phoneNumber = bsr.readLine();
			System.out.print("Enter your loginName   : ");
			String loginName = bsr.readLine();
			
			wCon.registerUser(new WalletUser(userId,userName,password,phoneNumber,loginName));
		    paymentWallet();
	    	}
	    	catch(Exception e) {e.printStackTrace();}
	}

	private void login() {
		System.out.println(" 1. Login As User\n 2. Login As Admin\n");
		int choice1;
		try {
			choice1 = Integer.parseInt(bsr.readLine());
			switch (choice1) {
			case 1:
				loginUser();
				break;
			case 2:
				loginAdmin();
				break;		
			default :
			throw new WalletException("Wrong Choice Entered");			
			}
		} 
		catch (NumberFormatException e) {
			e.printStackTrace();
		}
		catch (IOException e) {
			e.printStackTrace();
		} 
		catch (WalletException e) {			
			System.out.println(exceptionOccured + e.getMessage()+"\n");
//			login();
		}
		
	}
	
	private void loginAdmin() {
		
			try {
				
				System.out.println("\nEnter the Admin UserId :");
				int aid = Integer.parseInt(bsr.readLine());
				System.out.println("Enter the Admin Password :");
				String apass = bsr.readLine();
				String isSignedIn=wCon.loginAdmin(aid, apass);
				
				if(isSignedIn.equalsIgnoreCase("Logged In"))
				{
					System.out.println("\nWelcome to Admin Panel !");
					homePageAdmin();
				}	
				
				else {
					
					throw new WalletException("Invalid Password Or Invalid Admin User ID");				
				}
			} 
			catch (NumberFormatException e) {
				e.printStackTrace();
			} 
			catch (IOException e) {
				e.printStackTrace();
			} catch (WalletException e) {
				System.out.println(exceptionOccured + e.getMessage());
			}
			
			
	}
	
	private void loginUser() {
		try {
			System.out.print("Enter your User ID   : ");
			int id = Integer.parseInt(bsr.readLine());
			System.out.print("Enter your Password  : ");
			String password = bsr.readLine();
			
			WalletUser currentUser=wCon.loginUser(id, password);
			
			if(currentUser!=null) {
				System.out.println("\nWelcome "+currentUser.getLoginName());
				homePageUser(currentUser);
			}
			else
				paymentWallet();
				
			
			
		} catch (IOException e) {
			
			e.printStackTrace();
		}
	 
	}

	public void homePageAdmin() {
		try {
			int input5=0;
			do {
				System.out.println("\nEnter Your choice : \n 1. Show Non-Approved Accounts\n 2. Show Approved Accounts\n 3. Approve Account\n 4. Show Account Details\n 5. Log Out ");
				input5 = Integer.parseInt(bsr.readLine());					
				switch (input5) {
					case 1: 
						wCon.showNonApprovedAccounts();							
						break;
					case 2:
						wCon.showApprovedAccounts();
						break;
					case 3:
						System.out.println("\nEnter the Account ID you want to approve");
						int appAccID=Integer.parseInt(bsr.readLine());
						wCon.approveAccount(appAccID);
						break;
					case 4:
						wCon.showAccountDetails();
						break;
					case 5:System.out.println("\nYou Have Been Logged Out As Admin\n");
						paymentWallet();
						break;
					default:
						throw new WalletException("Invalid Input !");

					}	
			}while(input5!=5);
		}
		catch (NumberFormatException e) {
			e.printStackTrace();
		}
		catch (IOException e) {
			e.printStackTrace();
		} catch (WalletException e) {
			System.out.println(exceptionOccured+e.getMessage());
//			loginAdmin();
		}
		
	}
	
	public void homePageUser(WalletUser currentUser) {
		try {
			int choice1=0;
			do {
			System.out.println(" 1. Send Money \n 2. Add Money to Wallet \n 3. Withdraw Money From Wallet \n 4. Show Wallet Balance \n 5. Show Transactions \n 6. My Account \n 7. LogOut ");
			choice1 =Integer.parseInt(bsr.readLine());
			switch (choice1) {
			case 1:
				System.out.println("\nEnter the User ID of the user in whose account you want to sent money");
				int userid;
				userid=Integer.parseInt(bsr.readLine());
				System.out.println("\nEnter the amount you want to send\n");
				double amnt;
				amnt=Double.parseDouble(bsr.readLine());
				System.out.println(wCon.sendMoney(currentUser,userid,amnt));
				break;
				
			case 2:
	           System.out.println("Enter amount you want to add in your wallet");
	           double amount=Double.parseDouble(bsr.readLine());
	           wCon.addMoneyToWallet(currentUser,amount);
	           break;
	        
			case 3:
				System.out.println("Enter the amount you want to withdraw from your wallet");
				double amt=Double.parseDouble(bsr.readLine());
				wCon.withdrawMoneyFromWallet(currentUser, amt);
				break;
				
			case 4:
				wCon.showBalance(currentUser);
				break;
				
			case 5:
				wCon.showTransacations(currentUser);
				break;

			case 6:
				wCon.myAccount(currentUser);
				break;
			
			case 7:
				System.out.println("\nYou Have Been Logged Out As User\n");
				paymentWallet();
				break;	
			default:
				throw new WalletException("You Have Entered a Wrong Choice !");
				
			}
			
		}while(choice1!=6);
			
		}
		
		catch(Exception e) {
			System.out.println(exceptionOccured+e.getMessage());
//			loginUser();
		}
		

		
	}

}
