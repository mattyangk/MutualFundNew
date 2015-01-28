package formbeans;

import java.util.ArrayList;
import java.util.List;

import org.mybeans.form.FormBean;

public class ChangePwdForm extends FormBean {
    private String oldPassword;
	private String newPassword;
	private String rePassword;
	

	public String getOldPassword() {
		return oldPassword;
	}
	public void setOldPassword(String s) {
		this.oldPassword = trimAndConvert(s, "<>\"");
	}
	public String getNewPassword() {
		return newPassword;
	}
	public void setNewPassword(String s) {
		this.newPassword = trimAndConvert(s, "<>\"");
	}
	public String getRePassword() {
		return rePassword;
	}
	public void setRePassword(String s) {
		this.rePassword = trimAndConvert(s, "<>\"");
	}

	
	public List<String> getValidationErrors() {
		List<String> errors = new ArrayList<String>();
		
		if(oldPassword==null||oldPassword.length()==0){
			errors.add("Old Password is required");
		}

		else if (newPassword == null || newPassword.length() == 0) {
			errors.add("New Password is required");
		}
		else if (newPassword.length() > 60)  errors.add("The password can not be more than 60 characters");

		else if (rePassword == null || rePassword.length() == 0) {
			errors.add("Please confirm the new password");
		}
		else if (rePassword.length() > 60)  errors.add("The password can not be more than 60 characters");

		

		else if (!newPassword.equals(rePassword)) {
			errors.add("Passwords do not match");
		}

		if (errors.size() > 0) {
			return errors;
		}
		return errors;
	}

}
