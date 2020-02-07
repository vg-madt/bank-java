package bank;

import java.io.File;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;

public class TransactionManager extends AbstractManager {
	private static final String BASE_DIR = "/home/vijaytha/bankworkspace/bank/resources/transaction/";
	
	public Transaction deposit(Account account,Double amount) {
		
			Transaction transaction = new Transaction();
 
    		transaction.setAmount(amount);
    		
    		UUID uuid = UUID.randomUUID();
    		transaction.setId(uuid.toString());
    		Date date = new Date();
    		transaction.setDate(date);
    		transaction.setTransactionType("deposit");
    		transaction.setBalanceBefore(account.getBalance()); 
    		double balance = transaction.getBalanceBefore() + amount;
    		transaction.setBalanceAfter(balance);
    		File dir = new File(BASE_DIR+account.getAccountNumber());
    		if(!dir.exists()) {
    			dir.mkdir();
    			
    		}
    		write(BASE_DIR+account.getAccountNumber()+"/"
    				+transaction.getTransactionType()+"_"+transaction.getId()+".json",transaction);
    		return transaction;
    	    		
    	}
	public Transaction withdraw(Account account,Double amount) {
		
		Transaction transaction = new Transaction();
    		transaction.setAmount(amount);
    		
    		UUID uuid = UUID.randomUUID();
    		transaction.setId(uuid.toString());
    		Date date = new Date();
    		transaction.setDate(date);
    		transaction.setTransactionType("withdraw");
    		transaction.setBalanceBefore(account.getBalance()); 
    		double balance = transaction.getBalanceBefore() - amount;
    		transaction.setBalanceAfter(balance);
    		File dir = new File(BASE_DIR+account.getAccountNumber());
    		if(!dir.exists()) {
    			dir.mkdir();
    			
    		}
    		write(BASE_DIR+account.getAccountNumber()+"/"
    				+transaction.getTransactionType()+"_"+transaction.getId()+".json",transaction);
    		
    			
   
    		
    		return transaction;		
    	}
	public List<Transaction> findLatestTransactions(Integer accountNumber) {
		String dirLocation = BASE_DIR+accountNumber+"/";
		File dir = new File(dirLocation);
		File[] files = dir.listFiles();
		long[] modified = new long [files.length];
		int i = 0;
		Map<Long, File> map = new HashMap<>(); //create key value pair for file and lastmodified
		for (File file : files) {
			modified[i] = file.lastModified();
			map.put(modified[i], file);
			
			i++;
			
		}
		Arrays.sort(modified);
		List <Transaction> transactions = new ArrayList<>();
		for(i=files.length-1;i>=files.length-5;i--) {
			File file = map.get(modified[i]);
			transactions.add(read(file.getAbsolutePath(),Transaction.class));
			
		}
		return transactions;
	}
}
	
    	
 

