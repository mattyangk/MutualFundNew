package controller;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.genericdao.RollbackException;

import util.ConvertUtil;
import model.FundPriceHistoryDAO;
import model.Model;
import model.CustomerDAO;
import model.PositionDAO;
import model.FundDAO;
import databeans.*;

public class EmployeeViewCustomerAction  extends Action{
	CustomerDAO customerDAO;
	PositionDAO positionDAO;
	FundDAO fundDAO;
	FundPriceHistoryDAO fundPriceHistoryDAO;
	
	public EmployeeViewCustomerAction(Model model){
		customerDAO = model.getCustomerDAO();
		positionDAO = model.getPositionDAO(); 
		fundDAO = model.getFundDAO();
		fundPriceHistoryDAO = model.getFundPriceHistoryDAO();
	}
	
	public String getName() {
		return "showCustomerInfo.do";
	}

	public String perform(HttpServletRequest request){
		List<String> errors = new ArrayList<String>();
		request.setAttribute("errors",errors);
		String messages;
		
		try{
			String customerName=(String)request.getParameter("customername");
		    CustomerBean theCustomer=customerDAO.getCustomerByUsername(customerName);
		
		    if(theCustomer==null){
				errors.add("No Such Customer!");
				return "viewAccountCustomer.jsp";
			}
		    
			int CustomerID=theCustomer.getCustomer_id();
			PositionBean [] Positions=positionDAO.getPositionsByCustomerId(CustomerID);
						
			request.setAttribute("customer",theCustomer);
			
			Date latestDay = fundPriceHistoryDAO.findLatestDate();			
			request.setAttribute("latestDay", latestDay);
			
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
					CustomerFundsInfoBean customerFundInfo = new CustomerFundsInfoBean();
					FundPriceHistoryBean price = fundPriceHistoryDAO.read(Positions[i].getFund_id(), latestDay);
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
			return "viewAccountCustomer.jsp";
		} 
		
		return "viewAccountCustomer.jsp";
	}

}
