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
import model.PositionDAO;
import model.TransactionDAO;

import org.genericdao.RollbackException;
import org.mybeans.form.FormBeanException;
import org.mybeans.form.FormBeanFactory;

import util.ConvertUtil;
import databeans.CustomerBean;
import databeans.FundBean;
import databeans.FundPriceHistoryBean;
import databeans.PositionAndFundBean;
import databeans.PositionBean;
import databeans.TransactionBean;
import exception.SharesOutOfBoundException;
import formbeans.SellFundForm;

public class SellFundAction extends Action {

	private FormBeanFactory<SellFundForm> formBeanFactory = FormBeanFactory
			.getInstance(SellFundForm.class);

	private PositionDAO positionDAO;
	private FundDAO fundDAO;
	private TransactionDAO transactionDAO;
	private CustomerDAO customerDAO;
	private FundPriceHistoryDAO fundPriceHistoryDAO;

	public SellFundAction(Model model) {
		positionDAO = model.getPositionDAO();
		fundDAO = model.getFundDAO();
		transactionDAO = model.getTransactionDAO();
		customerDAO = model.getCustomerDAO();
		fundPriceHistoryDAO = model.getFundPriceHistoryDAO();
	}

	@Override
	public String getName() {
		return "sellFund.do";
	}

	@Override
	public String perform(HttpServletRequest request) {

		List<String> errors = new ArrayList<String>();
		List<String> successes = new ArrayList<String>();
		request.setAttribute("errors", errors);
		request.setAttribute("successes", successes);

		try {
			SellFundForm form = formBeanFactory.create(request);
			request.setAttribute("form", form);
			
			HttpSession session = request.getSession();
			
			CustomerBean customer = (CustomerBean) session
					.getAttribute("customer");
			
			if (customer == null) {
				errors.add("session expired");
				return "index.do";
			}
			
			CustomerBean latestCustomer = customerDAO.read(customer.getCustomer_id());
			request.setAttribute("customer", latestCustomer);			
			
			FundBean[] funds = fundDAO.getAllFunds();
			
			if(funds == null){
				System.out.println("no funds created !");
				errors.add("There are no funds to sell!");
				return "sellFund.jsp";
			}
			
			ArrayList<PositionAndFundBean> positionAndFunds = new ArrayList<PositionAndFundBean>();
			Date latestDay = fundPriceHistoryDAO.findLatestDate();
			for (int i = 0; i < funds.length; i++) {
				PositionBean position = positionDAO.read(funds[i].getFund_id(), customer.getCustomer_id());
				if (position == null) {
					continue;
				}
				FundPriceHistoryBean price = fundPriceHistoryDAO.read(funds[i].getFund_id(), latestDay);
				PositionAndFundBean positionAndFund = new PositionAndFundBean();
				positionAndFund.setAvailable_shares(position.getAvailable_shares());
				positionAndFund.setCustomer_id(customer.getCustomer_id());
				positionAndFund.setFund_id(funds[i].getFund_id());
				positionAndFund.setName(funds[i].getName());
				positionAndFund.setShares(position.getShares());
				positionAndFund.setSymbol(funds[i].getSymbol());
				positionAndFund.setPrice(price.getPrice());
				positionAndFunds.add(positionAndFund);
				
				System.out.println("test  " + positionAndFund.getAvailable_shares());
			}
			
			if(positionAndFunds == null || positionAndFunds.size() ==0){
				System.out.println("no funds to sell !");
				errors.add("There are no funds to sell!");
				return "sellFund.jsp";
			}
			
			request.setAttribute("positionAndFunds", positionAndFunds.toArray(new PositionAndFundBean[positionAndFunds.size()]));

			if (!form.isPresent()) {
				System.out.println("No param");
				return "sellFund.jsp";
			}

			// Any validation errors?
			errors.addAll(form.getValidationErrors());
			if (errors.size() != 0) {
				return "sellFund.jsp";
			}
			
			System.out.println("Fund name in form is " + form.getFundname());
			
			
			FundBean fund = fundDAO.getFundByName(form.getFundname());

			if(fund == null){
				errors.add("Wrong fund name");
				return "sellFund.jsp";
			}
			positionDAO.updateAvailableShares(fund.getFund_id(), customer.getCustomer_id(), form.getShareAsDouble());
			TransactionBean transaction = new TransactionBean();
			transaction.setCustomer_id(customer.getCustomer_id());
			transaction.setFund_id(fund.getFund_id());
			transaction.setIs_complete(false);
			transaction.setIs_success(false);
			transaction.setShares(ConvertUtil.coverShareDoubleToLong(form.getShareAsDouble()));
			transaction.setTransaction_date(new Date());
			transaction.setTrasaction_type("sell");
			
			transactionDAO.createAutoIncrement(transaction);
			successes.add("Your transaction is in process !");


		} catch (FormBeanException e) {
			errors.add(e.getMessage());
			return "sellFund.jsp";
		} catch (RollbackException e) {
			errors.add(e.getMessage());
			return "sellFund.jsp";
		} catch (SharesOutOfBoundException e) {
			errors.add(e.getMessage());
			return "sellFund.jsp";
		} catch (Exception e) {
			errors.add(e.getMessage());
			return "sellFund.jsp";
		}

		return "manage.jsp";
	}

}
