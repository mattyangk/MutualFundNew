package controller;

import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import model.CustomerDAO;
import model.Model;

import org.genericdao.RollbackException;
import org.mybeans.form.FormBeanException;
import org.mybeans.form.FormBeanFactory;

import databeans.CustomerBean;
import formbeans.ChangePwdForm;

public class CustomerChangePwdAction extends Action{
	private FormBeanFactory<ChangePwdForm> formBeanFactory = FormBeanFactory
			.getInstance(ChangePwdForm.class);

	private CustomerDAO customerDAO;

	public CustomerChangePwdAction(Model model) {
		customerDAO = model.getCustomerDAO();
	}

	public String getName() {
		return "CustomerChangePwd.do";
	}

	public String perform(HttpServletRequest request) {
		// Set up error list
		List<String> errors = new ArrayList<String>();
		List<String> successes = new ArrayList<String>();
		request.setAttribute("errors", errors);
		request.setAttribute("successes", successes);

		try {
			HttpSession session = request.getSession();
			CustomerBean customer = (CustomerBean) session.getAttribute("customer");
			
			if (customer == null) {
				errors.add("session expired");
				return "index.do";
			}

			// Load the form parameters into a form bean
			ChangePwdForm form = formBeanFactory.create(request);

			// If no params were passed, return with no errors so that the form
			// will be
			// presented (we assume for the first time).
			if (!form.isPresent()) {
				return "customerChangePwd.jsp";
			}

			// Check for any validation errors
			errors.addAll(form.getValidationErrors());
			if (errors.size() != 0) {
				return "customerChangePwd.jsp";
			}
			String oldPwd=form.getOldPassword();
			String firstPwd = form.getNewPassword();
			String secondPwd = form.getRePassword();
			
			if(!oldPwd.equals(customer.getPassword()))
			{
				errors.add("Please type the correct old password!");
				return "customerChangePwd.jsp";
			}
			
		
			if (!firstPwd.equals(secondPwd)) {
				errors.add("Two passwords are not the same. Please enter again");
				return "customerChangePwd.jsp";
			}
			
			customer.setPassword(firstPwd);			
			customerDAO.update(customer);
			
			successes.add("Password has been changed!");
			
			
		} catch (RollbackException e) {
			errors.add(e.toString());
			return "customerChangePwd.jsp";
		} catch (FormBeanException e) {
			errors.add(e.toString());
			return "customerChangePwd.jsp";
		}
		
		return "manage.jsp";
	}

}
