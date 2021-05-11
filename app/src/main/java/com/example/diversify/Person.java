package com.example.diversify;

public class Person
{
    public static final double DEFAULT_SALARY = 50000.00;
    public static final double DEFAULT_EXPENSES = 35000.00;
    //    public static final double DEFAULT_DEBT = 15000.00;
    //    public static final double DEFAULT_INTEREST_RATE = 1.03;
    public static final double DEFAULT_OVERTIME_BONUS = 2000;
    public static final double DEFAULT_INITIAL_CASH = 10000.00;
    private double salary;
    private double expenses;
    private double debt;
    private double interestRate;
    private double overtimeBonus;
    
    public Person()
    {
        setSalary(DEFAULT_SALARY);
        setExpenses(DEFAULT_EXPENSES);
//        setDebt(DEFAULT_DEBT);
//        setInterestRate(DEFAULT_INTEREST_RATE);
        this.overtimeBonus = DEFAULT_OVERTIME_BONUS;
    }

    
    
    
    
    
    
    
    
    //////////////////////////////// GETTERS AND SETTERS ///////////////////////////////
    public double getSalary() {
        return salary;
    }

    public void setSalary(double salary) {
        this.salary = salary;
    }

    public double getExpenses() {
        return expenses;
    }

    public void setExpenses(double expenses) {
        this.expenses = expenses;
    }

//    public double getDebt() {
//        return debt;
//    }
//
//    public void setDebt(double debt) {
//        this.debt = debt;
//    }
//
//    public double getInterestRate() {
//        return interestRate;
//    }
//
//    public void setInterestRate(double interestRate) {
//        this.interestRate = interestRate;
//    }

	/**
	 * This is the getter for the overtimeBonus field
	 * @return the overtimeBonus
	 */
	public double getOvertimeBonus() {
		return overtimeBonus;
	}

	/**
	 * This is the setter method for the overtimeBonus
	 * @param overtimeBonus the overtimeBonus to set
	 */
	public void setOvertimeBonus(double overtimeBonus) {
		this.overtimeBonus = overtimeBonus;
	}
}
