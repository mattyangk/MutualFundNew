package controller;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.genericdao.RollbackException;

import util.ConvertUtil;
import databeans.CustomerBean;
import databeans.CustomerFundsInfoBean;
import databeans.FundBean;
import databeans.FundPriceHistoryBean;
import databeans.PositionBean;
import model.CustomerDAO;
import model.FundDAO;
import model.FundPriceHistoryDAO;
import model.Model;
import model.PositionDAO;

public class ViewCustomerAccountAction extends Action {

	CustomerDAO customerDAO;
	PositionDAO positionDAO;
	FundDAO fundDAO;
	FundPriceHistoryDAO fundPriceHistoryDAO;

	public ViewCustomerAccountAction(Model model) {
		customerDAO = model.getCustomerDAO();
		positionDAO = model.getPositionDAO(); 
		fundDAO = model.getFundDAO();
		fundPriceHistoryDAO = model.getFundPriceHistoryDAO();
	}

	@Override
	public String getName() {
		return "viewAccount.do";
	}

	@Override
	public String perform(HttpServletRequest request) {
		List<String> errors = new ArrayList<String>();
		request.setAttribute("errors",errors);
		String messages;

		HttpSession session = request.getSession();
		CustomerBean customer = (CustomerBean)session.getAttribute("customer");

		try{
			
			int CustomerID=customer.getCustomer_id();
			PositionBean [] Positions=positionDAO.getPositionsByCustomerId(CustomerID);
			Date latestDay = fundPriceHistoryDAO.findLatestDate();
			
			request.setAttribute("latestDay", latestDay);
			
			customer = customerDAO.read(customer.getCustomer_id());
			session.setAttribute("customer", customer);
			
			if(customer==null){
				return "manage.jsp";
			}
			if(Positions==null || latestDay == null)
			{
				messages="The customer does not have any fund yet";
				request.setAttribute("message", messages);
				return "viewAccountCustomer.jsp";
			}
			
			else{
				CustomerFundsInfoBean [] fundInfo=new CustomerFundsInfoBean[Positions.length];
				
				for(int i=0;i<fundInfo.length;i++)
				{
					FundBean theFund=fundDAO.getFundById(Positions[i].getFund_id());
					FundPriceHistoryBean price = fundPriceHistoryDAO.read(Positions[i].getFund_id(), latestDay);
					CustomerFundsInfoBean customerFundInfo = new CustomerFundsInfoBean();
					fundInfo[i] = customerFundInfo;
					fundInfo[i].setFund_id(theFund.getFund_id());
					fundInfo[i].setFund_name(theFund.getName());
					fundInfo[i].setFund_symbol(theFund.getSymbol());
					fundInfo[i].setShares(Positions[i].getShares());
					fundInfo[i].setAvailable_shares(Positions[i].getAvailable_shares());
					fundInfo[i].setPrice(price.getPrice());
					double total = ConvertUtil.convertAmountLongToDouble(fundInfo[i].getPrice()) * ConvertUtil.convertShareLongToDouble(fundInfo[i].getShares());
					total = new BigDecimal(total).setScale(2, BigDecimal.ROUND_HALF_UP).doubleValue();
					fundInfo[i].setTotal(ConvertUtil.convertAmountDoubleToLong(total));
			
				}
				
				request.setAttribute("fundInfo",fundInfo);
			}

		}catch (RollbackException e) {
			errors.add(e.getMessage());
			return "manage.jsp";
		}
			return "viewAccountCustomer.jsp";
		}

	}
