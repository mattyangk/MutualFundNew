package databeans;

import java.util.Date;

import org.genericdao.PrimaryKey;

@PrimaryKey("transaction_id")
public class TransactionBean {
	private int transaction_id;
	private int customer_id;
	private int fund_id;
	private Date execute_date;
	private Date transaction_date;
	private long shares;
	private String trasaction_type;
	private long amount;
	private boolean is_complete;
	private boolean is_success;
	
	public int getTransaction_id() {
		return transaction_id;
	}
	public void setTransaction_id(int transaction_id) {
		this.transaction_id = transaction_id;
	}
	public int getCustomer_id() {
		return customer_id;
	}
	public void setCustomer_id(int customer_id) {
		this.customer_id = customer_id;
	}
	public int getFund_id() {
		return fund_id;
	}
	public void setFund_id(int fund_id) {
		this.fund_id = fund_id;
	}
	public Date getExecute_date() {
		return execute_date;
	}
	public void setExecute_date(Date execute_date) {
		this.execute_date = execute_date;
	}
	public Date getTransaction_date() {
		return transaction_date;
	}
	public void setTransaction_date(Date transaction_date) {
		this.transaction_date = transaction_date;
	}
	public long getShares() {
		return shares;
	}
	public void setShares(long shares) {
		this.shares = shares;
	}
	public String getTrasaction_type() {
		return trasaction_type;
	}
	public void setTrasaction_type(String trasaction_type) {
		this.trasaction_type = trasaction_type;
	}
	public long getAmount() {
		return amount;
	}
	public void setAmount(long amount) {
		this.amount = amount;
	}
	public boolean isIs_complete() {
		return is_complete;
	}
	public void setIs_complete(boolean is_complete) {
		this.is_complete = is_complete;
	}
	public boolean isIs_success() {
		return is_success;
	}
	public void setIs_success(boolean is_success) {
		this.is_success = is_success;
	}

}
