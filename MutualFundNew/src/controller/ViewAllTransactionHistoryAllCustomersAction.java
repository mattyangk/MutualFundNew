package controller;

import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.genericdao.RollbackException;

import databeans.CustomerBean;
import databeans.FundBean;
import databeans.FundPriceHistoryBean;
import databeans.TransactionAndPriceBean;
import databeans.TransactionBean;
import model.CustomerDAO;
import model.FundDAO;
import model.FundPriceHistoryDAO;
import model.Model;
import model.TransactionDAO;

public class ViewAllTransactionHistoryAllCustomersAction extends Action {

	private CustomerDAO customerDAO;
	private TransactionDAO transactionDAO;
	private FundDAO fundDAO;
	private FundPriceHistoryDAO fundPriceHistoryDAO;

	public ViewAllTransactionHistoryAllCustomersAction(Model model) {
		customerDAO = model.getCustomerDAO();
		transactionDAO = model.getTransactionDAO();
		fundDAO = model.getFundDAO();
		fundPriceHistoryDAO = model.getFundPriceHistoryDAO();
	}

	@Override
	public String getName() {
		return "viewAllTransactionsHistory.do";
	}

	@Override
	public String perform(HttpServletRequest request) {
		List<String> errors = new ArrayList<String>();
		request.setAttribute("errors",errors);

		HttpSession session = request.getSession();
		CustomerBean customer = (CustomerBean) session.getAttribute("customer");

		TransactionBean[] allTransactions = null;
		TransactionAndPriceBean[] allTransactionWithPrices = null;
		try {
			allTransactions = transactionDAO
					.getAllTransactions();
			if (allTransactions == null) {
				errors.add("No Transactions !");
				return "viewAllTransactionHistoryAllCustomers.jsp";
			} else {
				allTransactionWithPrices = new TransactionAndPriceBean[allTransactions.length];
				for (int i = 0; i < allTransactions.length; i++) {

					TransactionAndPriceBean transactionWithPrice = new TransactionAndPriceBean();
					transactionWithPrice.setAmount(allTransactions[i]
							.getAmount());
					transactionWithPrice.setCustomer_id(allTransactions[i]
							.getCustomer_id());
					transactionWithPrice.setExecute_date(allTransactions[i]
							.getExecute_date());
					transactionWithPrice.setFund_id(allTransactions[i]
							.getFund_id());
					transactionWithPrice.setIs_complete(allTransactions[i]
							.isIs_complete());
					transactionWithPrice.setIs_success(allTransactions[i]
							.isIs_success());
					transactionWithPrice.setShares(allTransactions[i]
							.getShares());
					transactionWithPrice.setTransaction_date(allTransactions[i]
							.getTransaction_date());
					transactionWithPrice.setTransaction_id(allTransactions[i]
							.getTransaction_id());
					transactionWithPrice.setTrasaction_type(allTransactions[i]
							.getTrasaction_type());
					CustomerBean tranCustomer = customerDAO.read(allTransactions[i].getCustomer_id());
					transactionWithPrice.setCustomer_name(tranCustomer.getUsername());

					if (allTransactions[i].getFund_id() > 0) {
						FundBean fund = fundDAO.read(allTransactions[i]
								.getFund_id());
						transactionWithPrice.setFund_name(fund.getName());
						if (allTransactions[i].getExecute_date() != null) {
							FundPriceHistoryBean price = fundPriceHistoryDAO
									.read(allTransactions[i].getFund_id(),
											allTransactions[i]
													.getExecute_date());
							transactionWithPrice.setPrice(price.getPrice());
						}
					}

					allTransactionWithPrices[i] = transactionWithPrice;
				}
			}
			request.setAttribute("allTransactionsHistory",
					allTransactionWithPrices);


		}catch (RollbackException e) {
			errors.add(e.getMessage());
			return "manage.jsp";
		}
		return "viewAllTransactionHistoryAllCustomers.jsp";
	}

}
