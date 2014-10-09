/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package sudokugenerator;

/**
 *
 * @author nabg
 */
public class CellArray {

    private CellValue[][] data;
    private int solvedCount;
    private boolean simplified;

    public CellArray() {
        data = new CellValue[9][9];
        for (int i = 0; i < 9; i++) {
            for (int j = 0; j < 9; j++) {
                data[i][j] = new CellValue();
            }
        }
        solvedCount = 0;
        simplified = false;
    }

    public void setUnknown(int row, int col) {
        data[row][col] = new CellValue();
        solvedCount--;
    }

    public int getSolved() {
        return solvedCount;
    }

    public void startCheckCycle() {
        simplified = false;
    }

    public boolean isSimplified() {
        return simplified;
    }

    public void flagSimplified(int row, int col, int val) {
        simplified = true;
    }

    public void setValue(int row, int col, int val) {
        data[row][col] = new CellValue(val);
    }

    public void genSetValue(int row, int col, int val) {
        data[row][col] = new CellValue(val);
        // This version called by generator - which is randomly picking and inserting
        // values.  It fills in all columns or a row, then moves on to next row
        // Reduce number of false combinations that it pursues by eliminating this value
        // from rest of current row and rest of current column.
        // (The generator calls the solve() method that does this work
        // in a more laborious manner.  This is just a minor efficiency.
        for (int k = col + 1; k < 9; k++) {
            data[row][k].setFalse(val);
        }
        // And eliminate in same column for higher number rows.
        for (int s = row + 1; s < 9; s++) {
            data[s][col].setFalse(val);
        }
    }

    public boolean isSolved(int row, int col) {
        return data[row][col].isSolved();
    }

    public int getValue(int row, int col) {
        return data[row][col].getValue();
    }

    public void setFalse(int row, int col, int possibleval) {
        if (data[row][col].isThisValue(possibleval)) {
            // Illegal - cell[row][col] has the value it's been told to setFalse
            // Can happen in the generation sequence
            return;
        }
        if (!data[row][col].couldBeThisValue(possibleval)) {
            return;
        }
        data[row][col].setFalse(possibleval);
        simplified = true;

        if (data[row][col].isSolved()) {
            solvedCount++;
        }
    }

    public int[] getChoices(int row, int col) {

        return data[row][col].getChoices();
    }

    public int getNumChoices(int row, int col) {

        return data[row][col].getNumChoices();
    }

    public boolean couldBeThisValue(int row, int col, int val) {
        return data[row][col].couldBeThisValue(val);
    }

    public boolean isThisValue(int row, int col, int val) {
        return data[row][col].isThisValue(val);
    }
}
