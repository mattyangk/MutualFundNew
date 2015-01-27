package formbeans;

import java.util.ArrayList;
import java.util.List;

import org.mybeans.form.FormBean;

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
        if (username.trim().length() > 60)  errors.add("The username can not be more than 60 characters");
        
        if (password == null || password.length() == 0) errors.add("Password is required");
        if (password.length() > 60)  errors.add("The password can not be more than 60 characters");
        
        if (firstname.trim().length() > 60)  errors.add("The firstname can not be more than 60 characters");
        
        if (lastname.trim().length() > 60)  errors.add("The lastname can not be more than 60 characters");

        return errors;
    }
	
	
}
