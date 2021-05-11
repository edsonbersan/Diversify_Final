package com.example.diversify;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import java.util.Calendar;
import java.util.HashMap;
import java.util.Random;

public class Tradeable
{

	protected int liquidity;
    protected String typeReturn;
    protected String name;
    protected int quantity = 0;
    protected ValueSimulator valueSimulator;
    private InvestmentType investmentType;
    protected int myId;
    protected String datePurchased;
    
    public Tradeable()
    {
        setLiquidity(liquidity);
        this.name = RandomName.generateRandomName();
        datePurchased = "N/A";
        Random random = new Random();
        myId = random.nextInt();
    }

    @Override
    public boolean equals(@Nullable Object obj) {
        if (obj == null || obj.getClass() != this.getClass()) {
            return false;
        }
        Tradeable ob = (Tradeable) obj;
        return this.myId == ob.getMyId();
    }

    public void updateValue(int turns) {
    	this.valueSimulator.runSimulation(turns);
    }

    @NonNull
    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();

        sb.append("Name: ").append(name);
        sb.append(", Value: ").append(String.format("$%.2f", valueSimulator.getCurrentValue()));
        sb.append(", Cost: ").append(String.format("$%.2f", valueSimulator.getInitialValue()));
        sb.append(", Return: ").append(String.format("%.2f%%", valueSimulator.getReturnRate() * 100));
        sb.append(", Volatility: ").append(String.format("%.2f%%", valueSimulator.getVolatility() * 100));
        sb.append(", Quantity: ").append(quantity);

        return sb.toString();
    }

    public String toStringInvestmentOpportunity() {
        StringBuilder sb = new StringBuilder();

        sb.append("Name: ").append(name);
        sb.append("\t Cost: ").append(String.format("$%.2f", valueSimulator.getInitialValue()));
        sb.append("\t Return: ").append(String.format("%.2f%%", valueSimulator.getReturnRate() * 100));
        sb.append("\t Volatility: ").append(String.format("%.2f%%", valueSimulator.getVolatility() * 100));

        return sb.toString();
    }

    ////////////////////////////////////// GETTERS AND SETTERS ///////////////////////////

    public int getLiquidity() {
        return liquidity;
    }

    public void setLiquidity(int liquidity) {
        this.liquidity = liquidity;
    }

    public String getTypeReturn() {
        return typeReturn;
    }

    public void setTypeReturn(String typeReturn) {
        this.typeReturn = typeReturn;
    }


    public int getQuantity() {
        return quantity;
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }

    public String getName() {
        return name;
    }


    public double getValue() {
        return this.valueSimulator.getCurrentValue();
    }



	public double getRateOfReturn() {
		return this.valueSimulator.getReturnRate();
	}

	public String getRateOfReturnString() {
        return String.format("%.2f%%", this.getRateOfReturn() * 100);
    }

    public String getVolatilityString() {
        return String.format("%.2f%%", this.getVolatility() * 100);
    }

    public double getPL() {
        return (this.valueSimulator.getCurrentValue() - this.valueSimulator.getInitialValue()) * quantity;
    }

    public String getPLString() {
        return String.format("$%.2f", this.getPL());
    }

	public double getVolatility() {
		return this.valueSimulator.getVolatility();
	}


    public InvestmentType getInvestmentType() {
        return investmentType;
    }

    public void setInvestmentType(InvestmentType investmentType) {
        this.investmentType = investmentType;
    }

    public String getTypeString() {
        switch (this.investmentType) {
            case STOCKS:
                return "Stock";
            case OPTIONS:
                return "Option";
            case BONDS:
                return "Bond";
        }
        return "Error at getTypeString";
    }

    public int getMyId() {
        return myId;
    }

    public String getDatePurchased()
    {
        return datePurchased;
    }

    public void setDatePurchased(String datePurchased)
    {
        this.datePurchased = datePurchased;
    }
}
