package com.example.diversify;

import java.util.Calendar;
import java.util.HashMap;

/**
 * Game state class stores.... THE GAME STATE! 
 * Such Game State will be serializable and stored inside android
 * The game state will store the following objects:
 * 
 * Portfolio, which represents the player's bought stocks.
 * Person, which represents the player's information.
 * Opportunities, which represents the current investment opportunities.
 * 
 * 
 * @author Edson Bersan
 */
public class GameState{

	
	// STORED OBJECTS.
	private Portfolio folio;
	private Person person;
	private Opportunities opportunities;
	private Turn turn;
	private HashMap<Integer, Tradeable> tradeablePool;
	private Settings settings;
	private int saveTime;

	
	public GameState() {
		tradeablePool = new HashMap<Integer, Tradeable>();
		settings = new Settings();
		this.folio = new Portfolio(tradeablePool, Person.DEFAULT_INITIAL_CASH);
		this.person = new Person();
		this.opportunities = new Opportunities(tradeablePool);
		this.turn = new Turn();
		this.saveTime = 0;
	}

	public void updateTradeablePool(HashMap<Integer, Tradeable> tradeablePool) {
		this.tradeablePool = tradeablePool;
		this.folio.tradablePool = tradeablePool;
		this.opportunities.tradablePool = tradeablePool;
	}

	public HashMap<Integer, Tradeable> getTradeablePool() {
		return tradeablePool;
	}

	/**
	 * This function will simulate turns.
	 * By doing that, it will need to take care of the following:
	 *
	 * How many years passed -> Update cash accordingly
	 * Should we update assets and work overtime?
	 *
	 *
	 * @param increment
	 */
	public void simulateTurns(MainActivity mainActivity, int increment) {
		turn.updateTurns(increment);
		folio.updatePortfolio(increment);

		/////// Giving Salary /////////////////
		setCash(getCash() + turn.yearsPassed() * (this.person.getSalary() - this.person.getExpenses()));

		///// Setting Overtime
		if (turn.isOvertimeAvailable()) { // This function already sets fields to false if they are true
			/// Set Work Button available!
			mainActivity.isWorkButtonAvailable = true;
		}

		if (turn.isAssetsAvailable()) { // This function already sets fields to false if they are true
			// updateAssets
			opportunities.generateNewOppor(5);
		}

		mainActivity.updateGui();
	}

	public void earnOvertime() {
		setCash(getCash() + person.getOvertimeBonus());
	}
	
	/// GETTERS AND SETTERS /////////////
	/**
	 * This is the getter for the folio field
	 * @return the folio
	 */
	public Portfolio getFolio() {
		return folio;
	}

	/**
	 * This is the getter for the person field
	 * @return the person
	 */
	public Person getPerson() {
		return person;
	}

	/**
	 * This is the getter for the opportunities field
	 * @return the opportunities
	 */
	public Opportunities getOpportunities() {
		return opportunities;
	}

	/**
	 * This is the getter for the turn field
	 * @return the turn
	 */
	public long getTurn() {
		return this.turn.getTurn();
	}

	public Turn getTurnObject() {
		return this.turn;
	}

	public String buyTradeble(Tradeable tradeable, int quantity) {
		String rtn = folio.buy(tradeable, quantity);
		opportunities.getIdArrayList().remove(tradeable.getMyId());
		return rtn;
	}

	public String sellTradeble(Tradeable tradeable, int quantity) {
		return folio.sell(tradeable, quantity);
	}

	public double getCash() {
		return this.folio.getCash();
	}

	public void setCash(double cash) {
		this.folio.setCash(cash);
	}

	public Settings getSettings()
	{
		return settings;
	}

	public int getSaveTime()
	{
		return saveTime;
	}

	public void saveTime()
	{
		saveTime = (int) (Calendar.getInstance().getTimeInMillis()/1000);
	}

}
