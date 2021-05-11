package com.example.diversify;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Random;

/**
 * This class will store all the investment opportunities.
 */
public class Opportunities 
{
	private HashSet<Integer> oppor;
    public HashMap<Integer, Tradeable> tradablePool;
    
    public Opportunities(HashMap<Integer, Tradeable> tradablePool)
    {
        this.tradablePool = tradablePool;
    	oppor = new HashSet<Integer>();
    	
    	generateNewOppor(5);
    }


    /**
     * TODO: CLEAN LOST INVESTMENTS
     * @param quantity
     */
    public void generateNewOppor(int quantity) {

        for (Integer i: oppor)
        {
            tradablePool.remove(i);
        }
        oppor.clear();
        Random r = new Random();
		for(int i = 0; i < quantity; i++) {
            int x = r.nextInt(3);
            Tradeable tradeable;
            switch(x)
            {
                case 0:
                    tradeable = new Stock();
                    break;
                case 1:
                    tradeable = new Option();
                    break;
                case 2:
                    tradeable = new Bond();
                    break;
                default:
                    tradeable = new Stock();
                    break;
            }
		    tradablePool.put(tradeable.getMyId(), tradeable);
			oppor.add(tradeable.getMyId());
		}
	}
    


    public HashSet<Integer> getIdArrayList() {
        return oppor;
    }

    public String toString()
    {
        StringBuilder text = new StringBuilder();
        for (Integer i: oppor)
        {
            Tradeable t = tradablePool.get(i);
            text.append(t.toString());

        }
        return text.toString();
    }
}
