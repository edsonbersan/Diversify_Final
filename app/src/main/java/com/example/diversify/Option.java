package com.example.diversify;

import java.util.Random;

/**
 * This class takes care of the Stock investments.
 * Stocks are medium volatile and extreme liquid.
 * Its return type is through valorization and dividends
 * @author Edson Bersan
 */
public class Option extends Tradeable {

	///////////////////////////// STATIC FIELDS ///////////////////////////////////
	/**
	 * See Stock for reference
	 * Yes, the mean return is -20%...
	 * Took this number from my head
	 */
	public static final double OPTION_RETURN_MEAN = 0.8;
	
	/**
	 * High std for return
	 * 
	 * Combined, 68% chance it is between -40% and 120% return
	 */
	public static final double OPTION_RETURN_STD = 0.4;
	
	/**
	 * High std for Volatility
	 */
	public static final double OPTION_VOLATILITY_MEAN = 0.5;
	public static final double OPTION_VOLATILITY_STD = 1;

	
	///////////////////////////// END STATIC FIELDS ///////////////////////////////////
	
	
	
	
	public Option() {
		super();
		this.setInvestmentType(InvestmentType.OPTIONS);

		Random random = new Random();
		double returnRate = (random.nextGaussian() * OPTION_RETURN_STD) + OPTION_RETURN_MEAN;
		double volatility = (random.nextGaussian() * OPTION_VOLATILITY_STD) + OPTION_VOLATILITY_MEAN;
		
		// we must set minimum volatility for options
		if (volatility < 0.1) {
			volatility = 0.1;
		}
		
		// Initial Value will be a random number between 1 and 1000
		double initialValue = random.nextFloat() * 1000;
		super.valueSimulator = new ValueSimulator(volatility, returnRate, initialValue);
	}
}
