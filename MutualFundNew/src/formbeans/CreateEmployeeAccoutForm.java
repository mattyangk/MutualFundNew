package formbeans;

import java.util.ArrayList;
import java.util.List;

import org.mybeans.form.FormBean;

import util.CheckFormat;

public class CreateEmployeeAccoutForm extends FormBean{
	private String username;
	private String password;
	private String firstname;
	private String lastname;
	public String getUsername() {
		return username;
	}
	public void setUsername(String username) {
		this.username = trimAndConvert(username, "<>\"");
	}
	public String getPassword() {
		return password;
	}
	public void setPassword(String password) {
		this.password = trimAndConvert(password, "<>\"");
	}
	public String getFirstname() {
		return firstname;
	}
	public void setFirstname(String firstname) {
		this.firstname = trimAndConvert(firstname, "<>\"");
	}
	public String getLastname() {
		return lastname;
	}
	public void setLastname(String lastname) {
		this.lastname = trimAndConvert(lastname, "<>\"");
	}
	
    public List<String> getValidationErrors() {
        List<String> errors = new ArrayList<String>();

        if (username == null || username.trim().length() == 0) errors.add("Username is required");
        else if (! CheckFormat.isValidString(username)) errors.add("Invalid username. Can only contain a-z,A-Z,0-9,_");
        else if (username.trim().length() > 60)  errors.add("The username can not be more than 60 characters");
        
        if (password == null || password.length() == 0) errors.add("Password is required");
        else if (password.length() > 60)  errors.add("The password can not be more than 60 characters");
        
        if (firstname == null || firstname.trim().length() == 0) errors.add("Firstname is required");
        else if (firstname.trim().length() > 60)  errors.add("The firstname can not be more than 60 characters");
        
        if (lastname == null || lastname.trim().length() == 0) errors.add("Lastname is required");
        else if (lastname.trim().length() > 60)  errors.add("The lastname can not be more than 60 characters");

        return errors;
    }
	
	
}
