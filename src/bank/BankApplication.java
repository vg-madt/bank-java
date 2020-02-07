package bank;

import java.util.List;
import java.util.Scanner;

public class BankApplication {

	public static void main(String[] args) {
		Scanner in = new Scanner(System.in);
		login(in);
	}
	
	public static void login(Scanner in) {
		System.out.println("Enter the username");
		String username = in.next();
		UserManager manager = new UserManager();
		User user = manager.read(username);
		if(user == null) {
			System.out.println("Please enter valid username");
		}
		else {
			System.out.println("Enter the password");
			String password = in.next();
			if(user.getPassword().equals(password)) {
				page1(in,user);
			}
			else {
				System.out.println("Login failed");
				login(in);
			}
		}

	}
	public static int page1(Scanner in,User user) {
		System.out.println("Choose an option");
		System.out.println("1) Perform transactions");
		System.out.println("2) Change password");
		System.out.println("3) Change phone number");
		if(user.getType().equals("employee")) {
			System.out.println("4) Add user details");
			System.out.println("5) Add account details for existing user");
		}
		System.out.println("0) Exit....");
		page2(in,user);
		return 0;
	}
	
	public static void page2(Scanner in,User user) {
		AccountManager accountManager = new AccountManager();
		UserManager userManager = new UserManager();
		int choice = in.nextInt();
		switch(choice) {
		case 0: 
			System.out.println("Exiting the system");
			break;
		case 1:
			System.out.println("Choose your account:");
			int i = 1;
			int accountChoice;
			List<String> userAccounts = accountManager.findUserAccounts(user.getUsername());
			for (String string : userAccounts) {
				System.out.println(i+")  " +string);
				i++;
			}
			accountChoice = in.nextInt();
			if(accountChoice <= userAccounts.size()) {
			String finalUserAccount = userAccounts.get(accountChoice-1);
			page3(in,finalUserAccount,user);
			}
			else {
				System.out.println("Enter a valid option");
				page1(in,user);
			}
			break;
		case 2:
			userManager.changePassword(in,user);
			System.out.println("Password is changed");
			break;
		case 3:
			userManager.changePhoneNumber(in, user);
			System.out.println("Phone number is changed to "+user.getPhoneNumber());
			break;
		case 4:
			if(!user.getType().equals("employee")) {
				System.out.println("Please choose a valid option");
				page2(in,user);
			}
			else {
			User createdUser = userManager.create(in);
			System.out.println("User "+createdUser.getUsername()+" is created");
			accountManager.create(in,createdUser);
			System.out.println("Account is created");
			login(in);
			break;
			}
		case 5:
			if(!user.getType().equals("employee")) {
				System.out.println("Please choose a valid option");
				page2(in,user);
			}
			else {
			System.out.println("Enter username");
			String name = in.next();
			User existUser = userManager.read(name);
			accountManager.create(in,existUser);
			break;
			}
		default: 
			System.out.println("Please choose a valid option");
			page2(in,user);
		}

	}
	public static void page3(Scanner in,String finalUserAccount,User user) {
		int choice = 0;
		int accountNumber = Integer.parseInt(finalUserAccount);
		AccountManager accountManager = new AccountManager();
		String filePath = accountManager.searchFilePath(accountNumber);
		Account account = new Account();
		account = accountManager.read(filePath,Account.class);
		System.out.println();
		System.out.println("Please choose the transaction to perform");
		System.out.println("1) Display your balance");
		System.out.println("2) Deposit money to your account");
		System.out.println("3) Withdraw money from your account");
		System.out.println("4) Transfer money to other accounts");
		System.out.println("5) Display last 5 transactions");
		if(account.getAccountType().equals("chequing")) {
			System.out.println("6) Pay utility");
		}
		System.out.println("Choose any other number for previous menu");
		choice = in.nextInt();
		switch(choice) {
		case 1: 
			double balance = accountManager.displayBalance(account);
			System.out.println("Your account balance is "+balance);
			page3(in,finalUserAccount,user);
			break;
		case 2:
			balance = accountManager.deposit(in, account, user);
			System.out.println("Amount deposited to account");
			System.out.println("Balance after deposit is "+balance);
			page3(in,finalUserAccount,user);
			break;
		case 3:
			balance = accountManager.withdraw(in, account, user);
			System.out.println("Amount is withdrawn");
			System.out.println("Balance after withdraw is "+balance);
			
			page3(in,finalUserAccount,user);
			break;
		case 4:
			balance = accountManager.transfer(in, account, user);
			System.out.println("Amount is transferred");
			System.out.println("Balance after transfer is "+balance);

			page3(in,finalUserAccount,user);
			break;
			
		case 5: 
			System.out.println("Last five transaction details......");
			List <String> lastFive = accountManager.lastFiveTransaction(account);
			for (String string : lastFive) {
				System.out.println(string);
			}				
				page3(in,finalUserAccount,user);
				break;
		case 6:
			if(!account.getAccountType().equals("chequing")) {
				System.out.println("Please use a valid option");
				page1(in,user);
				break;
				
			}
			balance = accountManager.payUtility(in, account, user);
			System.out.println("Amount is transferred");
			System.out.println("Balance after transfer is "+balance);

			page3(in,finalUserAccount,user);
			break;
			
		default: 
			page1(in,user);
		}
		}
	}

