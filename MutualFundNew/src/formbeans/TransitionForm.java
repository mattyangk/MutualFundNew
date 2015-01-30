package formbeans;

import org.mybeans.form.FormBean;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class TransitionForm extends FormBean {
	private String transitionDate;
	private String[] fund_id;
	private String[] price;

	public String getTransitionDate() {
		return transitionDate;
	}

	public void setTransitionDate(String transitionDate) {
		this.transitionDate = transitionDate;
	}

	public String[] getFund_id() {
		return fund_id;
	}

	public void setFund_id(String[] fund_id) {
		this.fund_id = fund_id;
	}

	public String[] getPrice() {
		return price;
	}

	public void setPrice(String[] price) {
		this.price = price;
	}

	public Date getTransitionDateAsDate() {
		List<String> errors = new ArrayList<String>();
		SimpleDateFormat parser = new SimpleDateFormat("yyyy-MM-dd");
		parser.setLenient(false);
		Date date = null;
		try {
			date = parser.parse(transitionDate);
		} catch (ParseException e) {
			errors.add("Not a Valid Format for TransitionDate ");
		}
		return date;
	}

	public List<String> getValidationErrors() {

		List<String> errors = new ArrayList<String>();

		if (transitionDate == null) {
			errors.add("Trading data is required");
		} else if (!isValidDate(transitionDate)) {
			errors.add("Not a Valid Format for TransitionDate ");
		}

		if (fund_id == null) {
			errors.add("Fund id is required");
		}

		if (price == null) {
			errors.add("Price id is required");
		}

		if (price != null && fund_id != null && price.length != 0
				&& fund_id.length != 0) {
			for (int i = 0; i < price.length; i++) {
				String s = price[i];
				if (s == null || s.length() == 0) {
					errors.add("Please type new prices for all funds");
					break;
				} else {
					try {
						Double.parseDouble(price[i]);
					} catch (NumberFormatException e) {
						errors.add("Not a Valid Format for Fund Price");
						break;
					}
				}
			}

			for (int i = 0; i < fund_id.length; i++) {
				String s = fund_id[i];
				if (s == null || s.length() == 0) {
					errors.add("Please provide fund Ids for all funds");
					break;
				} else {
					try {
						Integer.parseInt(fund_id[i]);
					} catch (NumberFormatException e) {
						errors.add("Not a Valid Format for Fund Id ");
						break;
					}
				}
			}

		}
		return errors;
	}

	public boolean isValidDate(String s) {
		try {
			SimpleDateFormat sf = new SimpleDateFormat("yyyy-MM-dd");
			sf.setLenient(false);
			sf.parse(s);
			System.out.println("date parsed ! value : " + sf.parse(s));
		} catch (Exception ex) {
			return false;
		}
		return true;
	}

}
