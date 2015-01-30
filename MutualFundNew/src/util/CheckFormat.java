package util;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class CheckFormat {
	
	public static boolean isValidString(String input) {
		Pattern pattern = Pattern.compile("^[\\w|_]+$");
		Matcher matcher = pattern.matcher(input);
		return matcher.find();
	}

}
