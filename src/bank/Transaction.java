package bank;
import java.util.*;
public class Transaction {
	private double amount;
	private String Id;
	private Date date;
	private String transactionType;
	private double balanceBefore;
	private double balanceAfter;
	
	public double getBalanceBefore() {
		return balanceBefore;
	}
	public void setBalanceBefore(double balanceBefore) {
		this.balanceBefore = balanceBefore;
	}
	public String getTransactionType() {
		return transactionType;
	}
	public void setTransactionType(String transactionType) {
		this.transactionType = transactionType;
	}
	
	public double getBalanceAfter() {
		return balanceAfter;
	}
	public void setBalanceAfter(double balanceAfter) {
		this.balanceAfter = balanceAfter;
	}
	public double getAmount() {
		return amount;
	}
	public void setAmount(double amount) {
		this.amount = amount;
	}
	public String getId() {
		return Id;
	}
	public void setId(String id) {
		Id = id;
	}
	public Date getDate() {
		return date;
	}
	public void setDate(Date date) {
		this.date = date;
	}
	
	

}
