package bank;
import java.util.Scanner;

public class UserManager extends AbstractManager{
	
	private final static String BASE_DIR = "/home/vijaytha/bankworkspace/bank/resources/user/";
	 
    
    public User create(Scanner in) {
    	User user = new User();
    	System.out.println("Enter the user name");
    	final String username = in.next();
    	user.setUsername(username);
    	System.out.println("Enter the password");
    	String password = in.next();
    	user.setPassword(password);
    	System.out.println("Enter the user type employee or user");
    	String type = in.next();
    	user.setType(type);
    	System.out.println("Enter the phone number");
    	String phoneNumber = in.next();
    	user.setPhoneNumber(phoneNumber);
    	System.out.println("Enter the Email ID");
    	String emailId = in.next();
    	user.setEmailId(emailId);;
    	System.out.println("Enter the Address");
    	String address = in.next();
    	user.setAddress(address);
    	write(BASE_DIR+username+".json",user);
    	return user;
      	
    }
    public User read(final String username) {
    	return read(BASE_DIR+username+".json",User.class);
    	
    }
    public void changePassword(Scanner in,User user) {
    	System.out.println("Enter your old password");
    	String password = in.next();
    	if(user.getPassword().equals(password)) {
    		System.out.println("Enter your new password");
    		String newPassword = in.next();
    		user.setPassword(newPassword);
    		write(BASE_DIR+user.getUsername()+".json",user);
    	}
    	else {
    		System.out.println("Please enter the correct password");
    	}
    }
    public void changePhoneNumber(Scanner in,User user) {
    	System.out.println("Enter your phone number");
    	String phoneNumber = in.next();
    	user.setPhoneNumber(phoneNumber);
    	write(BASE_DIR+user.getUsername()+".json",user);
    	
    }
    

}