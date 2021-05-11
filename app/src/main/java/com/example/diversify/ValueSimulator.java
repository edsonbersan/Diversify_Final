package com.example.diversify;

import android.util.Log;

import java.util.ArrayList;
import java.util.Random;

import static android.content.ContentValues.TAG;

/**
 * This class is supposed to simulate the return/volatility model.
 * Each object has a state. Whenever an object is instantiated, it will have a base
 * return and volatility, based on which type of investment we are talking about.
 * Then, the model will update the state(i.e. "current value") of the investment according in timesteps
 * For sake of simplicity, it will model value as a normal curve
 * @author Edson Bersan
 */
public class ValueSimulator {
	
	/**
	 * This field stores the volatility of the investment. The volatility is the measurement of one
	 * standard deviation of the normal curve.
	 * IMPORTANT: RETURN IS MEASURED IN (1 + RETURN)!!!!! (e.g.: 10% return = 1.1)
	 */
	private double volatility;
	/**
	 * This field stores the return of the investment. This is the mean of the normal curve.
	 * IMPORTANT: RETURN IS MEASURED IN (1 + RETURN)!!!!! (e.g.: 10% return = 1.1)
	 */
	private double returnRate;
	
	/**
	 * This is more like a debbuging field, may be deleted later. This stores how many simulations the
	 * investment have been through
	 */
	private int timestep;
	
	/**
	 * This one is a bit complicated. Investments will be updated on a timely basis. For instance, stocks are updated
	 * every 10 seconds; Funds, every minute and so on.
	 * However, volatility and return are annualized(which in our app, is daily (or weekly. TODO: adjust this comment)).
	 * Thus, in order to keep volatility and return rates annualized, this indicates the size of the
	 * timestep. For instance, timestepSize of 1 means that the "runSimulation" function will apply
	 * the full volatility to the model. However, a timeStepSize of 0.1 means that the "runSimulation" will need
	 * to be ran 10 times to simulate an "year".
	 * The two formulas for volume and return are: (got these from my own python code) (Daily == Yearly)
	 * timestepSize = 1 / NUM_OF_YEARLY_STEPS
	 * STEP_VOLATILITY = YEARLY_VOLATILITY / sqrt(NUM_OF_YEARLY_STEPS) =
	 * STEP_VOLATILITY = YEARLY_VOLATILITY * sqrt(timestepSize)  (Forgeeet)
	 * and
	 * STEP_RETURN = YEARLY_RETURN**(1/NUM_OF_YEARLY_STEPS) =
	 * STEP_RETURN = YEARLY_RETURN**(timestepSize) (Forgeeet)
	 *
	 */
	private double currentValue;
	private Random generator;
	private double initialValue;
	private final double NUM_OF_YEARLY_STEPS = Turn.TURNS_PER_YEAR;

	private ArrayList<Double> pastValues;
	
	/**
	 * This constructor sets the internal fields and generates a new Random object.
	 * @param volatility
	 * @param returnRate
	 * @param initialValue
	 */
	public ValueSimulator(double volatility, double returnRate, double initialValue) {
		this.volatility = volatility;
		this.returnRate = returnRate;
		this.currentValue = initialValue;
		this.generator = new Random();
		this.initialValue = initialValue;
		pastValues = new ArrayList<Double>();
	}
	
	
	/**
	 * This method is the main method of the class. It will be responsible for updating the current
	 * value of the investment. It returns the updated current value for convenience.
	 * @param numOfSimulations
	 * @return
	 */
	public double runSimulation(int numOfSimulations) {
		if (numOfSimulations == 0) {
			//pastValues.add(currentValue);
			return this.currentValue;
		}
		/*
		 *  First we calculate the volatility and return we are going to apply to this simulation
		 *  
		 *  Instead of running a for loop with the num of simulations, we may simplify the model
		 *  by multiplying timestepSize * numOfSimulations.
		 */
		double simSTD = this.volatility / Math.sqrt( (NUM_OF_YEARLY_STEPS) / numOfSimulations);
		double simMean = Math.pow( 1 + this.returnRate, numOfSimulations / NUM_OF_YEARLY_STEPS);
		
		double rawMultiplier = this.generator.nextGaussian();
		Log.d("RUNSimulation", "runSimulationMULTB4: " + rawMultiplier);
		rawMultiplier *= simSTD;
		rawMultiplier += simMean;

		//rawMultiplier += 1;

		Log.d("RUNSimulation", "runSimulationMEAN: " + simMean);
		Log.d("RUNSimulation", "runSimulationSTD: " + simSTD);
		Log.d("RUNSimulation", "runSimulationMULT: " + rawMultiplier);
		
		this.currentValue *= rawMultiplier;
		this.timestep += numOfSimulations;
		
		if (this.currentValue <= 0) {
			this.currentValue = 0;
		}
		//pastValues.add(currentValue);
		return this.currentValue;
	}

	
	
	////////////////////// GETTERS AND SETTERS /////////////////////////////////
	/**
	 * This is the getter for the volatility field
	 * @return the volatility
	 */
	public double getVolatility() {
		return volatility;
	}

	/**
	 * This is the setter method for the volatility
	 * @param volatility the volatility to set
	 */
	public void setVolatility(double volatility) {
		this.volatility = volatility;
	}

	/**
	 * This is the getter for the returnRate field
	 * @return the returnRate
	 */
	public double getReturnRate() {
		return returnRate;
	}

	/**
	 * This is the setter method for the returnRate
	 * @param returnRate the returnRate to set
	 */
	public void setReturnRate(double returnRate) {
		this.returnRate = returnRate;
	}

	/**
	 * This is the getter for the timestep field
	 * @return the timestep
	 */
	public int getTimestep() {
		return timestep;
	}

	/**
	 * This is the getter for the currentValue field
	 * @return the currentValue
	 */
	public double getCurrentValue() {
		return currentValue;
	}


	/**
	 * This is the setter method for the currentValue
	 * @param currentValue the currentValue to set
	 */
	public void setCurrentValue(double currentValue) {
		this.currentValue = currentValue;
	}

	public double getInitialValue() {
		return initialValue;
	}
}
