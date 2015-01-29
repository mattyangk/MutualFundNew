package databeans;


public class CustomerFundsInfoBean {
	private int fund_id;
	private String fund_name;
	private String fund_symbol;
	private long shares;
	private long available_shares;
	private long price;
	private long total;
	
	public long getAvailable_shares() {
		return available_shares;
	}
	public void setAvailable_shares(long available_shares) {
		this.available_shares = available_shares;
	}
	public int getFund_id() {
		return fund_id;
	}
	public void setFund_id(int fund_id) {
		this.fund_id = fund_id;
	}
	public String getFund_name() {
		return fund_name;
	}
	public void setFund_name(String fund_name) {
		this.fund_name = fund_name;
	}
	public String getFund_symbol() {
		return fund_symbol;
	}
	public void setFund_symbol(String fund_symbol) {
		this.fund_symbol = fund_symbol;
	}
	public long getShares() {
		return shares;
	}
	public void setShares(long shares) {
		this.shares = shares;
	}
	public long getPrice() {
		return price;
	}
	public void setPrice(long price) {
		this.price = price;
	}
	public long getTotal() {
		return total;
	}
	public void setTotal(long total) {
		this.total = total;
	}
	

}
