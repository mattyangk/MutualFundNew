package util;

public class ConvertUtil {

	public static double convertAmountLongToDouble(long amount) {
		return (double)amount / 100;
	}
	
	public static long convertAmountDoubleToLong(double amount) {
		return (long) (amount * 100);
	}
	
	public static double convertShareLongToDouble(long share) {
		return (double)share / 1000;
	}
	
	public static long coverShareDoubleToLong(double share) {
		return (long) (share * 1000);
	}
	
}
