package controller;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import model.CustomerDAO;
import model.FundDAO;
import model.FundPriceHistoryDAO;
import model.Model;
import model.TransactionDAO;

import org.genericdao.RollbackException;
import org.mybeans.form.FormBeanException;
import org.mybeans.form.FormBeanFactory;

import util.ConvertUtil;
import databeans.CustomerBean;
import databeans.FundBean;
import databeans.FundPriceDetailBean;
import databeans.FundPriceHistoryBean;
import databeans.TransactionBean;
import exception.AmountOutOfBoundException;
import formbeans.BuyFundForm;

public class BuyFundAction extends Action {
	private FormBeanFactory<BuyFundForm> formBeanFactory = FormBeanFactory
			.getInstance(BuyFundForm.class);
	private FundDAO fundDAO;
	private CustomerDAO customerDAO;
	private TransactionDAO transactionDAO;
	private FundPriceHistoryDAO fundPriceHistoryDAO;

	public BuyFundAction(Model model) {
		fundDAO = model.getFundDAO();
		fundPriceHistoryDAO = model.getFundPriceHistoryDAO();
		customerDAO = model.getCustomerDAO();
		transactionDAO = model.getTransactionDAO();
	}

	public String getName() {
		return "buyFund.do";
	}

	public String perform(HttpServletRequest request) {
		List<String> errors = new ArrayList<String>();
		List<String> successes = new ArrayList<String>();
		request.setAttribute("errors", errors);
		request.setAttribute("successes", successes);

		try {

			HttpSession session = request.getSession();
			CustomerBean customer = (CustomerBean) session
					.getAttribute("customer");

			if (customer == null) {
				errors.add("session expired");
				return "index.do";
			}

			CustomerBean latestCustomer = customerDAO.read(customer);
			request.setAttribute("balance", ConvertUtil
					.convertAmountLongToDouble(latestCustomer.getBalance()));

			FundBean[] fundsWithoutPrice = fundDAO.getAllFunds();
			FundPriceDetailBean[] funds = new FundPriceDetailBean[fundsWithoutPrice.length];
			Date latestDay = fundPriceHistoryDAO.findLatestDate();
			if (fundsWithoutPrice != null) {
				for (int i = 0; i < fundsWithoutPrice.length; i++) {
					FundPriceDetailBean fundPrice = new FundPriceDetailBean();
					fundPrice.setFund_id(fundsWithoutPrice[i].getFund_id());
					fundPrice.setName(fundsWithoutPrice[i].getName());
					fundPrice.setSymbol(fundsWithoutPrice[i].getSymbol());
					if (latestDay != null) {
						FundPriceHistoryBean priceHistory = fundPriceHistoryDAO
								.read(fundsWithoutPrice[i].getFund_id(),
										latestDay);
						// the fund is just created and no latest price
						if (priceHistory == null) {
							fundPrice.setPrice_date(null);
							fundPrice.setPrice(0);
						} else {
							fundPrice.setPrice_date(latestDay);
							fundPrice.setPrice(priceHistory.getPrice());
						}
					} else {
						fundPrice.setPrice_date(null);
						fundPrice.setPrice(0);
					}

					funds[i] = fundPrice;
				}

			}
			request.setAttribute("funds", funds);

			BuyFundForm form = formBeanFactory.create(request);
			request.setAttribute("form", form);

			System.out.println(form.getFundname());

			// If no params were passed, return with no errors so that the form
			// will be
			// presented (we assume for the first time).
			if (!form.isPresent()) {
				return "buyFund.jsp";
			}

			// Any validation errors?
			errors.addAll(form.getValidationErrors());
			if (errors.size() != 0) {
				return "buyFund.jsp";
			}

			// Look up the user
			FundBean fund = fundDAO.getFundByName(form.getFundname());

			if (fund == null) {
				errors.add("No such fund.");
				return "buyFund.jsp";
			}

			customerDAO.updateBalance(customer.getCustomer_id(),
					form.getFundAmountAsDouble());

			TransactionBean transaction = new TransactionBean();
			transaction.setCustomer_id(customer.getCustomer_id());
			transaction.setAmount(ConvertUtil.convertAmountDoubleToLong(form
					.getFundAmountAsDouble()));
			transaction.setFund_id(fund.getFund_id());
			transaction.setIs_complete(false);
			transaction.setIs_success(false);
			transaction.setTransaction_date(new Date());
			transaction.setTrasaction_type("buy");

			transactionDAO.createAutoIncrement(transaction);

			successes.add("Your transaction is in process !");

		} catch (RollbackException e) {
			errors.add(e.getMessage());
			return "buyFund.jsp";
		} catch (FormBeanException e) {
			errors.add(e.getMessage());
			return "buyFund.jsp";
		} catch (AmountOutOfBoundException e) {
			errors.add(e.getMessage());
			return "buyFund.jsp";
		}

		return "manage.jsp";
	}
}
