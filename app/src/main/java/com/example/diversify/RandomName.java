package com.example.diversify;

import java.util.Hashtable;
import java.util.Random;

public class RandomName 
{	
	//private static Hashtable<String, String> table = new Hashtable<String, String>();
	
    public static String generateRandomName()
    {
        Random r = new Random();
        String alphabet = "ABCDEFGHIJKLMNOPQRSTUVWXYZ";
        int x = r.nextInt(3);
        String ticker = "";
        for (int i = 0; i < 3+x; i++)
        {
        	ticker += alphabet.charAt(r.nextInt(alphabet.length()));
        }
        //if (table.containsKey(ticker))
        //{
        //	return generateRandomName();
        //}
        //else
        //{
    	//	table.put(ticker, "");
        //}
        return ticker;
    }
}