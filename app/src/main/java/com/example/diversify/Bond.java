package com.example.diversify;

import java.util.Random;

/**
 * This class takes care of the Stock investments.
 * Stocks are medium volatile and extreme liquid.
 * Its return type is through valorization and dividends
 * @author Edson Bersan
 */
public class Bond extends Tradeable {

	///////////////////////////// STATIC FIELDS ///////////////////////////////////
	/**
	 * See Stock for reference
	 * Bond is safer, thus return is 0.5% - 2.5%
	 */
	public static final double BOND_RETURN_MEAN = 0.015;
	
	/**
	 * Low std for return
	 */
	public static final double BOND_RETURN_STD = 0.01;
	
	/**
	 * Low std for Volatility
	 */
	public static final double BOND_VOLATILITY_MEAN = 0.007; // 0.7%
	public static final double BOND_VOLATILITY_STD = 0.003;
	
	///////////////////////////// END STATIC FIELDS ///////////////////////////////////
	
	
	public Bond() {
		super();
		this.setInvestmentType(InvestmentType.BONDS);
		Random random = new Random();
		double returnRate = (random.nextGaussian() * BOND_RETURN_STD) + BOND_RETURN_MEAN;
		double volatility = (random.nextGaussian() * BOND_VOLATILITY_STD) + BOND_VOLATILITY_MEAN;
		
		// we must set minimum volatility for bonds
		if (volatility < 0.001) {
			volatility = 0.001;
		}
		
		// Initial Value will be a random number between 1 and 1000
		double initialValue = random.nextFloat() * 1000;
		this.valueSimulator = new ValueSimulator(volatility, returnRate, initialValue);
	}
}
