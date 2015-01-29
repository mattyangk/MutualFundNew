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
		double amt = amount;
		BigDecimal bdAmount = new BigDecimal(amount);
		bdAmount = bdAmount.setScale(2, RoundingMode.HALF_UP);
		amt = bdAmount.doubleValue();
		System.out.println("amt. after rounding : "+amt);
		System.out.println("Amount being returned : "+((long)(amt * 100)));
		return (long) (amt * 100);
	}
	
	public static double convertShareLongToDouble(long share) {
		return (double)share / 1000;
	}
	
	public static long coverShareDoubleToLong(double share) {
		System.out.println();
		System.out.println("shares before rounding : "+share);
		double shr = share;
		BigDecimal bdShare = new BigDecimal(share);
		bdShare = bdShare.setScale(3, RoundingMode.HALF_UP);
		shr = bdShare.doubleValue();
		System.out.println("shares after rounding : "+shr);
		System.out.println("Shares being returned : "+((long)(shr * 1000)));
		return (long) (shr * 1000);
	}
	
}
