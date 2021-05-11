package com.example.diversify;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.HashMap;
import java.util.HashSet;

public class Portfolio 
{
    private HashSet<Integer> folioIdList;
    private double cash;
    public HashMap<Integer, Tradeable> tradablePool;
    private long turn;
    
    public Portfolio(HashMap<Integer, Tradeable> tradablePool, double initialCash)
    {
        this.tradablePool = tradablePool;
        cash = initialCash;
        folioIdList = new HashSet<Integer>();
    }

    public void updatePortfolio() {
        updatePortfolio(1);
    }

    public void updatePortfolio(int num) {
        for(Integer i: folioIdList) {
            assert tradablePool != null;
            Tradeable t = tradablePool.get(i);
            assert t != null;
            t.updateValue(num);
        }
    }

    public HashSet<Integer> getInternalFolio() {
        return folioIdList;
    }

    public String buy(Tradeable t, int quantity)
    {
        Tradeable tradeable = tradablePool.get(t.myId);
        assert tradeable != null;
    	double cost = tradeable.getValue();
        if (cash < quantity * cost)
        {
            return "Not Enough Money!";
        }

        cash -= quantity * cost;
        tradeable.setDatePurchased("Turn: " + String.valueOf(turn));
        tradeable.setQuantity(tradeable.getQuantity() + quantity);
        folioIdList.add(t.getMyId());
        return "Bought "+quantity+" units of "+tradeable.getName();
    }
    
    public String sell(Tradeable t, int quantity)
    {
        Tradeable tradeable = tradablePool.get(t.myId);
        assert tradeable != null;

        if (tradeable.getQuantity() < quantity) {
            return "Not Enough Units!";
        }

        tradeable.setQuantity(tradeable.getQuantity() - quantity);
        cash += quantity * tradeable.getValue();
        if (tradeable.getQuantity() == 0) {
            folioIdList.remove(tradeable.getMyId());
        }
        return "Sold "+quantity+" units of "+t.getName();
    }
    
    public String toString()
    {
        StringBuilder text = new StringBuilder("Available Cash: " + cash + "\n");
        for (Integer i: folioIdList)
        {
            Tradeable t = tradablePool.get(i);
//            text.append(t.getName()).append(":\t$").append(Double.toString(t.getValue())).append("\n");
            text.append(t.toString());

        }
        return text.toString();
    }

    public double getCash() {
        return this.cash;
    }
    public void setCash(double cash) {
        this.cash = cash;
    }
    public void setTurn(long turn)
    {
        this.turn = turn;
    }
    
}

