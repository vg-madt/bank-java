package bank;

import java.io.File;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class AccountManager extends AbstractManager {
	private static final String BASE_DIR = "/home/vijaytha/bankworkspace/bank/resources/account/";
	public void create(Scanner in,User user) {
		System.out.println("Enter the account type");
		String accountType = in.next();
		System.out.println(accountType);
		if(accountType.equals("chequing")) {
			ChequingAccount chequing = new ChequingAccount();
			System.out.println("Enter the Account number");
			Integer accountNumber = in.nextInt();
			chequing.setAccountNumber(accountNumber);
			System.out.println("Enter the Balance");
			double balance = in.nextDouble();
			chequing.setBalance(balance);
			chequing.setAccountType(accountType);
			write(BASE_DIR+user.getUsername()+"_"+accountNumber+".json",chequing);
		}
		else if(accountType.equals("saving")) {
			SavingAccount saving = new SavingAccount();
			System.out.println("Enter the Account number");
			Integer accountNumber = in.nextInt();
			saving.setAccountNumber(accountNumber);
			System.out.println("Enter the Balance");
			double balance = in.nextDouble();
			saving.setBalance(balance);
			saving.setAccountType(accountType);
			write(BASE_DIR+user.getUsername()+"_"+accountNumber+".json",saving);
		}
		else {
			System.out.println("Enter chequing or saving");
		}
	}
	
	public void read(final String username,String accountType,Integer accountNumber) {
		if(accountType.equalsIgnoreCase("chequing")) {
			read(BASE_DIR+username+"_"+accountNumber+".json",ChequingAccount.class);
		}
		else if(accountType.equalsIgnoreCase("saving")) {
			read(BASE_DIR+username+"_"+accountNumber+".json",SavingAccount.class);	
		}
    	
	}
	
	public double displayBalance(Account account) {
		return account.getBalance();
	}
	
	public double deposit(Scanner in,Account account,User user) {
		TransactionManager transactionManager = new TransactionManager();
		System.out.println("Please enter you amount to deposit");
		double amount = in.nextDouble();
		Transaction transaction = transactionManager.deposit(account,amount);
		double balance = transaction.getBalanceAfter();
		account.setBalance(balance);
		write(BASE_DIR+user.getUsername()+"_"+account.getAccountNumber()+".json",account);
		
		return balance;
	}
	public double withdraw(Scanner in,Account account,User user) {
		TransactionManager transactionManager = new TransactionManager();
		System.out.println("Please enter you amount to withdraw");
		double amount = in.nextDouble();
		Transaction transaction = transactionManager.withdraw(account,amount);
		double balance = transaction.getBalanceAfter();
		account.setBalance(balance);
		write(BASE_DIR+user.getUsername()+"_"+account.getAccountNumber()+".json",account);
		
		return balance;
	}
	public double transfer(Scanner in,Account from,User user) {
		System.out.println("Enter the account number you want to transfer to");
		int toAccount = in.nextInt();
		System.out.println("Please enter the amount to transfer");
		double amount = in.nextDouble();
		TransactionManager transactionManager = new TransactionManager();
		Transaction transaction = transactionManager.withdraw(from,amount);
		double balance = transaction.getBalanceAfter();
		from.setBalance(balance);
		write(BASE_DIR+user.getUsername()+"_"+from.getAccountNumber()+".json",from);
		//deposit to toAccount
		String toFilePath = searchFilePath(toAccount);
		Account to = read(toFilePath,Account.class);
		transaction = transactionManager.deposit(to, amount);
		balance = transaction.getBalanceAfter();
		to.setBalance(balance);
		write(toFilePath,to);
		return from.getBalance();
	}
	
	public double payUtility(Scanner in,Account account,User user) {
		System.out.println("Enter the bill amount");
		double amount = in.nextDouble();
		TransactionManager transactionManager = new TransactionManager();
		Transaction transaction = transactionManager.withdraw(account,amount);
		double balance = transaction.getBalanceAfter();
		account.setBalance(balance);
		write(BASE_DIR+user.getUsername()+"_"+account.getAccountNumber()+".json",account);
		return balance;
	}
	
	public String searchFilePath(Integer accountNumber) {
		String dirLocation = BASE_DIR;
		File dir = new File(dirLocation);
		File[] files = dir.listFiles();
		for (File file : files) {
			String fileName = file.getName();
			if(fileName.endsWith("_"+accountNumber+".json")) {
				return file.getAbsolutePath();
			}
				
		}
		return null;
	}
	public List<String> findUserAccounts(String username) {
		String dirLocation = BASE_DIR;
		List<String> userAccounts = new ArrayList<String>();
		File dir = new File(dirLocation);
		File[] files = dir.listFiles();
		for (File file : files) {
			String fileName = file.getName();
			if(fileName.startsWith(username+"_")) {
				String string = fileName.substring(username.length()+1, fileName.length()-5);
				userAccounts.add(string);
			}	
		}
		return userAccounts;
	}
	
	public List<String> lastFiveTransaction(Account account){
		TransactionManager manager = new TransactionManager();
		
		List<String> lastFive = new ArrayList<>();
		List<Transaction> transactions = manager.findLatestTransactions(account.getAccountNumber());
		for (Transaction transaction : transactions) {
			String string = "Transaction ID: "+transaction.getId()+"\n"
					+"Transacted at: "+transaction.getDate()+"\n"
					+"Transaction type: "+transaction.getTransactionType()+"\n"
					+"Amount: "+transaction.getAmount()+"\n"
					+"Balance before: "+transaction.getBalanceBefore()+"\n"
					+"Balance after: "+transaction.getBalanceAfter()+"\n\n";
			lastFive.add(string);
		}
		return lastFive;
		
	}
	
}
