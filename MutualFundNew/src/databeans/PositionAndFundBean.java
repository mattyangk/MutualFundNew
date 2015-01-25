package databeans;

public class PositionAndFundBean {
	private int fund_id;
	private String name;
	private String symbol;
	private int customer_id;
	private long shares;
	private long available_shares;
	public int getFund_id() {
		return fund_id;
	}
	public void setFund_id(int fund_id) {
		this.fund_id = fund_id;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getSymbol() {
		return symbol;
	}
	public void setSymbol(String symbol) {
		this.symbol = symbol;
	}
	public int getCustomer_id() {
		return customer_id;
	}
	public void setCustomer_id(int customer_id) {
		this.customer_id = customer_id;
	}
	public long getShares() {
		return shares;
	}
	public void setShares(long shares) {
		this.shares = shares;
	}
	public long getAvailable_shares() {
		return available_shares;
	}
	public void setAvailable_shares(long available_shares) {
		this.available_shares = available_shares;
	}
	
}
