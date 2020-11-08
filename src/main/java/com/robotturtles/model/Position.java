package com.robotturtles.model;

public class Position {
    private int rowNumber;
    private int colNumber;

    public Position(int rowNumber, int colNumber) {
        this.rowNumber = rowNumber;
        this.colNumber = colNumber;
    }

    /**
     * Getter method to return the row number
     * @return rowNumber
     */
    public int getRowNumber() {
        return rowNumber;
    }

    /**
     * Getter method to return the column number
     * @return colNumber
     */
    public int getColNumber() {
        return colNumber;
    }

    @Override
    public boolean equals(Object o) {
        if (o == this) return true;
        if (!(o instanceof Position)) return false;
        Position y = (Position) o;
        return this.rowNumber == y.getRowNumber() && this.colNumber == y.getColNumber();
    }
}
