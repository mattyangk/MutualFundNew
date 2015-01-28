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
import model.FundDAO;
import model.FundPriceHistoryDAO;
import model.Model;
import model.TransactionDAO;

public class ViewTransactionHistoryAction extends Action {

	private TransactionDAO transactionDAO;
	private FundDAO fundDAO;
	private FundPriceHistoryDAO fundPriceHistoryDAO;

	public ViewTransactionHistoryAction(Model model) {
		transactionDAO = model.getTransactionDAO();
		fundDAO = model.getFundDAO();
		fundPriceHistoryDAO = model.getFundPriceHistoryDAO();
	}

	@Override
	public String getName() {
		return "transactionHistory.do";
	}

	@Override
	public String perform(HttpServletRequest request) {
		List<String> errors = new ArrayList<String>();
		request.setAttribute("errors", errors);

		HttpSession session = request.getSession();
		CustomerBean customer = (CustomerBean) session.getAttribute("customer");
		TransactionBean[] allTransactions = null;
		TransactionAndPriceBean[] allTransactionWithPrices = null;
		try {
			allTransactions = transactionDAO
					.getTransactionsByCustomerId(customer.getCustomer_id());

			if (allTransactions == null) {
				errors.add("No Transactions !");
				return "viewTransactionsHistory.jsp";
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
			request.setAttribute("transactionsHistory",
					allTransactionWithPrices);

		} catch (RollbackException e) {
			errors.add(e.getMessage());
			return "manage.jsp";
		}
		return "viewTransactionsHistory.jsp";
	}

}
