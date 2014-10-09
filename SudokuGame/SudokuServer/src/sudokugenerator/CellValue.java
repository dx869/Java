/*

 CellValue.java
 Based on original by eric.frankenberg@iut-tlse3.fr

 CellValue represents one square in Sudoku grid.
 * 
 * It has a set of 9 booleans - initially all true - that identify the
 * digits that might still be placed in this square.
 * It has a 'value' field and a 'solved' field - if have decided on a value
 * to go in this cell, these variables are set.
 * 
 */
package sudokugenerator;

public class CellValue {

    private boolean[] numberIsPossible = new boolean[9];
    private boolean solved = false;
    private int value = 0;
    private int numpos = 0;

    public CellValue() {
        // Unknown cell - any value possible
        resetPossibles();
    }

    private void resetPossibles() {
        // Any value is possible
        for (int i = 0; i < 9; i++) {
            numberIsPossible[i] = true;
        }
        numpos = 9;
    }

    public CellValue(int entry) {
        // Fixed cell, set value and array representing possiblities,
        // mark as a solved cell;
        // 'entry' is the digit value 1..9 (since arrays are zero based
        // it's [entry-1] that gets set of course)
        if (entry == 0) {
            System.out.println("Bug?");
            System.exit(1);
        }

        for (int i = 0; i < 9; i++) {
            numberIsPossible[i] = false;
        }
        numberIsPossible[entry - 1] = true;
        value = entry;
        solved = true;
        numpos = 1;
    }

    public boolean isSolved() {
        return solved;
    }

    public int getValue() {
        return value;
    }

    public void setFalse(int possibleval) {
// Called when comparing values in a row, or in a column, or in a block
        // Some other cell has claimed "possibleval" - so it cannot remain
        // a candidate for this cell - remove it.
        // After removal, check cell to see if only a single choice remains -
        // in which case mark it as solved.

        // May get called again after cell is solved as will be telling it
        // repeatedly of all the values that it cannot have.
        if (!solved && (numberIsPossible[possibleval - 1])) {
            // Have decided that 'possibleval' is no longer possible for this cell
            numberIsPossible[possibleval - 1] = false;
            numpos--;
            testIfSolved();
        }
    }

    private void testIfSolved() {
        // Another possiblity has been eliminated for current cell - maybe that leaaves only 1

        if ((numpos == 1) && !solved) {
            solved = true;
            // which is it
            for (int i = 0; i < 9; i++) {
                if (numberIsPossible[i]) {
                    value = i + 1;
                    break;
                }
            }
        }
    }

    public int[] getChoices() {
        // Create an array with the values that still could be placed in this
        // cell


        int count = 0;

        int[] choices = new int[numpos];
        count = 0;

        // Fill in the values that are possible
        for (int i = 0; i < 9; i++) {
            if (numberIsPossible[i]) {
                choices[count++] = i + 1;
            }
        }
        return choices;
    }

    public int getNumChoices() {
        return numpos;
    }

    public boolean couldBeThisValue(int val) {
        return numberIsPossible[val - 1];
    }

    public boolean isThisValue(int val) {
        return solved && (val == value);
    }
}
