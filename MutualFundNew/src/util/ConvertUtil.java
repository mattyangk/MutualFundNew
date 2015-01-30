package util;

import java.math.BigDecimal;
import java.math.RoundingMode;

public class ConvertUtil {

	public static double convertAmountLongToDouble(long amount) {
		return (double)amount / 100;
	}
	
	public static long convertAmountDoubleToLong(double amount) {
		System.out.println();
		System.out.println("amt. before rounding : "+amount);
		double amt = amount * 100;
		BigDecimal bdAmount = new BigDecimal(amt);
		bdAmount = bdAmount.setScale(2, RoundingMode.HALF_UP);
		amt = bdAmount.doubleValue();
		System.out.println("amt. after rounding : "+(long)amt);
		return (long)amt;
	}
	
	public static double convertShareLongToDouble(long share) {
		return (double)share / 1000;
	}
	
	public static long coverShareDoubleToLong(double share) {
		System.out.println();
		System.out.println("shares before rounding : "+share);
		double shr = share * 1000;
		BigDecimal bdShare = new BigDecimal(shr);
		bdShare = bdShare.setScale(3, RoundingMode.HALF_UP);
		shr = bdShare.doubleValue();
		System.out.println("shares after rounding : "+ (long)shr);
		return (long) shr;
	}
	
}
