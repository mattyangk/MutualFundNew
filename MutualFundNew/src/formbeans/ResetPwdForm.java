package formbeans;

import java.util.ArrayList;
import java.util.List;

import org.mybeans.form.FormBean;

public class ResetPwdForm extends FormBean {
	private String username;
	private String newPassword;
	private String rePassword;

	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = trimAndConvert(username, "<>\"");
	}

	public String getrePassword() {
		return rePassword;
	}

	public String getNewPassword() {
		return newPassword;
	}

	public void setrePassword(String s) {
		rePassword = trimAndConvert(s, "<>\"");
	}

	public void setNewPassword(String s) {
		newPassword = trimAndConvert(s, "<>\"");
	}

	public List<String> getValidationErrors() {
		List<String> errors = new ArrayList<String>();

		if (username == null || username.length() == 0) {
			errors.add("Username of customer is required");
		}

		if (newPassword == null || newPassword.length() == 0) {
			errors.add("New Password is required");
		} else if (newPassword.length() > 60)
			errors.add("The password can not be more than 60 characters");

		if (rePassword == null || rePassword.length() == 0) {
			errors.add("Please confirm the new password");
		} else if (newPassword.length() > 60) {
			errors.add("The password can not be more than 60 characters");
		} else if (!newPassword.equals(rePassword)) {
			errors.add("Passwords do not match");
		}

		if (errors.size() > 0) {
			return errors;
		}
		return errors;
	}
}
