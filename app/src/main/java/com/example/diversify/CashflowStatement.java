package com.example.diversify;

public class CashflowStatement {

    private class Item {
        private String name;
        private int turn;
        private double value;
        public Item(String name, int turn, double value) {
            this.name = name;
            this.turn = turn;
            this.value = value;
        }

        public String getName() {
            return name;
        }

        public int getTurn() {
            return turn;
        }

        public double getValue() {
            return value;
        }

        public String getValueString() {
            return String.format("$%.2f", this.value);
        }
    }



}
