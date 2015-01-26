package exception;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.text.DecimalFormat;

public class SharesOutOfBoundException extends Exception {

	private static final long serialVersionUID = 1L;
	
	double ownShares;
	
	double trasactionShares;
	
	public SharesOutOfBoundException(double ownShares, double transactionShares) {
		super();
		this.ownShares = ownShares;
		this.trasactionShares = transactionShares;	
	}
	
	@Override
	public String getMessage() {
		BigDecimal bdAmt = new BigDecimal(ownShares);
		bdAmt = bdAmt.setScale(3, RoundingMode.HALF_UP);
		double displayOwnAmount = bdAmt.doubleValue();
		
		BigDecimal bdTrans = new BigDecimal(trasactionShares);
		bdTrans = bdTrans.setScale(3, RoundingMode.HALF_UP);
		double displayTransAmt = bdTrans.doubleValue();
		
		DecimalFormat df = new DecimalFormat("0.000"); 
		
		return "Invalid Transaction !! Available Shares " + df.format(displayOwnAmount) + " is less than Requested Shares of "+ df.format(displayTransAmt) + " !!\n";
	}
	

}
