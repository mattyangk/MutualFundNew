package formbeans;

import org.mybeans.form.FormBean;

import util.CheckFormat;

import java.util.ArrayList;
import java.util.List;


public class CreateFundForm extends FormBean{

	private String fundname;
	private String symbol;

	public String getFundname() {
		return fundname;
	}


	public void setFundname(String fundname) {
		this.fundname = trimAndConvert(fundname, "<>\"");
	}


	public String getSymbol()
	{
		return symbol;
	}

	public void setSymbol(String symbol)
	{
		this.symbol=trimAndConvert(symbol, "<>\"");
	}


	public List<String>getValidationErrors() {
		List<String> errors=new ArrayList<String>();

		if(fundname==null||fundname.trim().length()==0)
			errors.add("Please type fundname");
		else if (! CheckFormat.isValidString(fundname)) errors.add("Invalid fundname. Can only contain a-z,A-Z,0-9,_");
		else if (fundname.trim().length() > 20)  errors.add("The fundname can not be more than 20 characters");

		if(symbol==null||symbol.trim().length()==0)
			errors.add("Please type Ticker");

		else if(symbol.length()<1||symbol.length()>5)
			errors.add("Ticker shoud be 1 to 5 characters.");

		if (fundname.matches(".*[<>\"].*") ) {
			errors.add("There should not be any angle brackets or quotes in fundname");
		}
		if (symbol.matches(".*[<>\"].*") ) {
			errors.add("There should not be any angle brackets or quotes in Ticker");
		}

		return errors;
	}


}
