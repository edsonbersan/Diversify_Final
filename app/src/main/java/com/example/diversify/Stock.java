package com.example.diversify;

import java.util.Random;

/**
 * This class takes care of the Stock investments.
 * Stocks are medium volatile and extreme liquid.
 * Its return type is through valorization and dividends
 * @author Edson Bersan
 */
public class Stock extends Tradeable{

	///////////////////////////// STATIC FIELDS ///////////////////////////////////

	/*
	 * IMPORTANT: ALL VALUES SHOULD BE REPRESENTED AS SIMPLE DECIMAL PERCENTAGE. NOT (1+percentage)!!!!
	 */
	/**
	 * This field indicates the base return of a stock.
	 * Basically, whenever a new stock investment opportunity is created, 
	 * it will have its own return and volatility. However, we use 
	 * a normal curve to determine those individual values.
	 * 
	 * With the current value the stocks returns will be around 10%, which is close to the
	 * S&P return of 13%.
	 * This means that if we made an index from the stocks generated, equally weighted, we would
	 * achieve a return of 10%
	 */
	public static final double STOCK_RETURN_MEAN = 0.05;  //  5%
	
	/**
	 * This field represent the standard deviation when calculating the stock return.
	 * With the current value of a whopping 10% of std and mean of 1.1, there is 68% chance that
	 * the generated stock return will be between -5% and 15% per year.
	 * BTW, I got this 10% out of my head.
	 */
	public static final double STOCK_RETURN_STD = 0.1; //  1%
	
	/**
	 * Same as the stock return, but with volatility.
	 * S&P Annual volatility is around 25%
	 */
	public static final double STOCK_VOLATILITY_MEAN = 0.25; //25%
	public static final double STOCK_VOLATILITY_STD = 0.1; // 10%
	
	/**
	 * Same as the stock return, but with volatility.
	 * S&P Annual volatility is around 25%
	 */
	public static final double STOCK_DIVIDEND_MEAN = 0.01; // 1%
	public static final double STOCK_DIVIDEND_STD = 0.005; // 0.5%
	
	private static final int STOCK_LIQUIDIDTY = 0;
	private static final String STOCK_TYPE_OF_RETURN = "DIVIDENDS/VAL";
	
	///////////////////////////// END STATIC FIELDS ///////////////////////////////////
	
	private double dividendYield;

	public Stock() {
		super();
		this.setInvestmentType(InvestmentType.STOCKS);
		Random random = new Random();
		double returnRate = (random.nextGaussian() * STOCK_RETURN_STD) + STOCK_RETURN_MEAN;
		double volatility = (random.nextGaussian() * STOCK_VOLATILITY_STD) + STOCK_VOLATILITY_MEAN;
		
		// we must set minimum volatility for stocks
		if (volatility < 0.05) { // Volatility less than 5%
			volatility = 0.05;
		}
		
		// Initial Value will be a random number between 1 and 1000
		double initialValue = random.nextFloat() * 1000;
		this.valueSimulator = new ValueSimulator(volatility, returnRate, initialValue);
		
		this.dividendYield = (random.nextGaussian() * STOCK_DIVIDEND_STD) + STOCK_DIVIDEND_MEAN;	
		if (dividendYield < 0) {
			this.dividendYield = 0;
		}
	}
}
