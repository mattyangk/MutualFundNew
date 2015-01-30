package util;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class CheckFormat {
	
	public static boolean isValidString(String input) {
		Pattern pattern = Pattern.compile("^[\\w|_]+$");
		Matcher matcher = pattern.matcher(input);
		return matcher.find();
	}

	public static boolean isValidAmount(String input) {
		Pattern patter = Pattern.compile("^-{0,1}\\d+\\.{0,1}(\\d{1,2}){0,1}$");
		Matcher matcher = patter.matcher(input);
		return matcher.find();
	}
	
	public static boolean isValidShare(String input) {
		Pattern patter = Pattern.compile("^-{0,1}\\d+\\.{0,1}(\\d{1,3}){0,1}$");
		Matcher matcher = patter.matcher(input);
		return matcher.find();
	}
	
}
