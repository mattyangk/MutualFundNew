package formbeans;

import java.util.ArrayList;
import java.util.List;

import org.mybeans.form.FormBean;

public class SearchCustomerNameForm extends FormBean {

	private String username;

	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = trimAndConvert(username, "<>\"");

	}

	public List<String> getValidationErrors() {
		List<String> errors = new ArrayList<String>();
		if (username.trim().length() == 0 || username == null) {
			errors.add("Please type the Customer's username");
		} else if (username.trim().length() > 60)
			errors.add("The username can not be more than 60 characters");
		return errors;
	}

}
