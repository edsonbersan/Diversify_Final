package com.example.diversify;


/**
 * We should implement the Async here
 */
public class Turn {

    //////////////////////////// TURN STATIC VARIABLES ////////////////////////////////
    /*
     * 1 hr = 1 month
     * 1 day = 2 years
     * Maximum idle time = 1 day = 2 years.
     */
    public static final int REFRESH_PERIOD_IN_SECONDS = 1; // Refresh on every 10 seconds (MUST BE A divisor of 60 (ie. 1, 2, 3, 5, 6, 10, 15, 20, 30, 60))
    public static final int TURNS_PER_REAL_MINUTE = 60 / REFRESH_PERIOD_IN_SECONDS; // DON'T CHANGE
    public static final int TURNS_PER_REAL_HOUR = 60 * TURNS_PER_REAL_MINUTE; // DON'T CHANGE

    public static final int REAL_HOURS_PER_YEAR = 12; // 12 hours = 1 year
    public static final int TURNS_PER_YEAR = TURNS_PER_REAL_HOUR * REAL_HOURS_PER_YEAR;

    public static final int MINUTES_PER_OVERTIME_SALARY = 60; // 1 Overtime every 60 minutes
    public static final int MINUTES_PER_OPPORTUNITIES_UPDATE = 60; // 1 Assets update every 60 minutes

    public static final int TURNS_PER_OVERTIME_SALARY = TURNS_PER_REAL_MINUTE * MINUTES_PER_OVERTIME_SALARY; // 1 Overtime every 60 minutes
    public static final int TURNS_PER_OPPORTUNITIES_UPDATE = TURNS_PER_REAL_MINUTE * MINUTES_PER_OPPORTUNITIES_UPDATE; // 1 Assets update every 60 minutes
    //////////////////////////////////////////////////////////////////////////////////////

    private int turn;


    private int yearsPassed;
    private int nextYear;
    private int nextOvertimeTurn;
    private int nextOpportunitiesUpdateTurn;

    private boolean overtimeAvailable;
    private boolean assetsAvailable;

    public Turn() {
        this.turn = 0;
        this.nextYear = TURNS_PER_YEAR;
        this.nextOvertimeTurn = TURNS_PER_OVERTIME_SALARY;
        this.nextOpportunitiesUpdateTurn = TURNS_PER_OPPORTUNITIES_UPDATE;
        overtimeAvailable = true;
        assetsAvailable = true;
    }

    public int yearsPassed() {
        int a = this.yearsPassed;
        this.yearsPassed = 0;
        return a;
    }


    /**
     * This function is the main function of turns
     * It is called inside Game State
     *
     * 1) It sets how many years passed between turn and turn + increment
     * 2) It sets whether a new OT bonus is available, and which turn is the next one
     * 3) it sets whether new assets should be calculated, and which turn is the next one
     * 4) it updates the turns counter
     * @param increment
     */
    public void updateTurns(int increment) {
        // 1)
        yearsPassed = ((turn + increment) / TURNS_PER_YEAR) - ((turn) / TURNS_PER_YEAR);

        // 2)
        if (turn + increment >= nextOvertimeTurn) {
            this.overtimeAvailable = true;
            /*
            * TODO: optimize this function, im lazy
             */
            while(nextOvertimeTurn <= (turn + increment)) {
                nextOvertimeTurn += TURNS_PER_OVERTIME_SALARY;
            }
        }

        // 3)
        if (turn + increment >= nextOpportunitiesUpdateTurn) {
            this.assetsAvailable = true;
            /*
             * TODO: optimize this function, im lazy
             */
            while(nextOpportunitiesUpdateTurn <= (turn + increment)) {
                nextOpportunitiesUpdateTurn += TURNS_PER_OPPORTUNITIES_UPDATE;
            }
        }

        // 4)
        this.turn += increment;


    }

    public boolean isAssetsAvailable() {
        if (assetsAvailable) {
            assetsAvailable = false;
            return true;
        }
        return false;
    }

    public boolean isOvertimeAvailable() {
        if (overtimeAvailable) {
            overtimeAvailable = false;
            return true;
        }
        return false;
    }

    public int getTurn() {
        return turn;
    }

}
