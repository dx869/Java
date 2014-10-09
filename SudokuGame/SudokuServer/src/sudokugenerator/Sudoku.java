/*
 Based on 

 " Sudoku.java

 Copyright Eric Frankenberg, 24 aout 2007 

 eric.frankenberg@iut-tlse3.fr"



 */
package sudokugenerator;

import java.util.Arrays;

public class Sudoku {

    private CellArray grid;

    public Sudoku() {
        grid = new CellArray();

    }

//    private void tracer() {
//        for (int i = 0; i < 9; i++) {
//            for (int j = 0; j < 9; j++) {
//                System.out.print(grid.getValue(i, j) + " ");
//            }
//            System.out.println();
//        }
//    }
    public void solveForLevel(int level) {
        switch (level) {
            case 4:
                solve();
                break;
            case 3:
                solveDifficult();
                break;
            case 2:
                solveAverage();
                break;
            case 1:
                solveEasy();
                break;
            default:
                solveVeryEasy();
                break;
        }
    }

    public void solve() {
        // Repeatedly apply all possible solutions strategies
        eliminateUsedValues();

        do {
            grid.startCheckCycle();

            pairs();
            triplets();
            onlyOnePosition();
            inBlockRowOrColumn();
            checkRowsAndColumns();

        } while (!isSolved() && grid.isSimplified());
    }

    public void solveDifficult() {
        // Repeatedly apply the simpler solution strategies - leaving
        // out those that search for groups of values

        eliminateUsedValues();
        do {
            grid.startCheckCycle();
            pairs();
            triplets();
            onlyOnePosition();
        } while (!isSolved() && grid.isSimplified());
    }

    public void solveAverage() {
        // Repeatedly apply the rules that look for things like locked pairs
        // and triplets

        eliminateUsedValues();
        do {
            grid.startCheckCycle();
            pairs();
            triplets();

        } while (!isSolved() && grid.isSimplified());
    }

    public void solveEasy() {
        // Only look for things like locked pairs (and of course any
        // cells that can only have one value)

        eliminateUsedValues();
        do {
            grid.startCheckCycle();
            pairs();

        } while (!isSolved() && grid.isSimplified());
    }

    public void solveVeryEasy() {
        // Just repeatedly check cells that seem only to have on possible
        // value


        do {
            grid.startCheckCycle();
            eliminateUsedValues();
        } while (!isSolved() && grid.isSimplified());
    }

    private boolean isSolved() {
        // Are all grid squares marked as solved?
        for (int i = 0; i < 9; i++) {
            for (int j = 0; j < 9; j++) {
                if (!grid.isSolved(i, j)) {
                    return false;
                }
            }
        }
        return true;
    }

    public CellArray getCellArray() {
        return grid;
    }

    private boolean checkRow(int row) {
        // Check whether specific row has all 9 numbers
        boolean[] hasVal = new boolean[10];  // Ignore element 0
        for (int i = 1; i < 10; i++) {
            hasVal[i] = false;
        }

        for (int i = 0; i < 9; i++) {
            int val = grid.getValue(row, i);
            if (hasVal[val]) {
                return false;
            }
            hasVal[val] = true;
        }

        return true;
    }

    private boolean checkRows() {
        for (int i = 0; i < 9; i++) {
            if (!checkRow(i)) {
                return false;
            }
        }
        return true;
    }

    private boolean checkColumn(int col) {

        // Check whether specific column has all 9 numbers

        boolean[] hasVal = new boolean[10];  // Ignore element 0
        for (int i = 1; i < 10; i++) {
            hasVal[i] = false;
        }

        for (int i = 0; i < 9; i++) {
            int val = grid.getValue(i, col);
            if (hasVal[val]) {
                return false;
            }
            hasVal[val] = true;
        }

        return true;
    }

    private boolean checkColumns() {
        for (int i = 0; i < 9; i++) {
            if (!checkColumn(i)) {
                return false;
            }
        }

        return true;
    }

    private boolean checkBlocks() {

        // Check that all the 3x3 blocks hold instances of the digits 1...9
        // 'counts' array will hold number of occurrences of each digit 1...9
        for (int g = 0; g < 3; g++) {
            for (int h = 0; h < 3; h++) {
                // Dealing with block [g][h] 
                
                boolean[] hasVal = new boolean[10];
                for (int i = 1; i < 10; i++) {
                    hasVal[i] = false;
                }

                for (int j = 0; j < 9; j++) {
                    int val = grid.getValue(3 * g + j % 3, 3 * h + j / 3);
                    if(hasVal[val]) return false;
                    hasVal[val]= true;
                }
               

            }
        }
        return true;
    }

    public boolean isValid() {

        // It is a valid solution if each row has all 9 digits, each column
        // has all 9 digits, and each 3x3 block has all 9 digits
        return checkRows() && checkColumns() && checkBlocks();
    }

    private void testRowForCell(int i, int j) {
        // Check other cells in same row - if any is marked as 'solved' remove
        // its value from those possible for cell[i][j]
        for (int k = 0; k < 9; k++) {
            if ((k != j) && (grid.isSolved(i, k))) {
                int val = grid.getValue(i, k);
                grid.setFalse(i, j, val);
            }
        }
    }

    private void testColumnForCell(int i, int j) {
        // Check other cells in same column - if any is marked as 'solved' remove
        // its value from those possible for cell[i][j]
        for (int k = 0; k < 9; k++) {
            if ((k != i) && (grid.isSolved(k, j))) {
                int val = grid.getValue(k, j);
                grid.setFalse(i, j, val);
            }
        }
    }

    private void testBlockForCell(int i, int j) {
        // Check other cells in same block - if any is marked as 'solved' remove
        // its value from those possible for cell[i][j]

        // First identify block (then convert back to row and column of top left cell
        // in block

        int blocki = i / 3;
        blocki *= 3;
        int blockj = j / 3;
        blockj *= 3;

        // Next examine all cells in 3x3 block (don't check cell against itself)
        for (int k = 0; k < 3; k++) {
            for (int l = 0; l < 3; l++) {
                int row = blocki + k;
                int col = blockj + l;
                if ((row == i) && (col == j)) {
                    continue;
                }
                if (grid.isSolved(row, col)) {
                    int val = grid.getValue(row, col);
                    grid.setFalse(i, j, val);
                }
            }
        }
    }

    private void eliminateUsedValues() {
        // Check every cell that is still marked as not yet solved
        //  For that cell -
        //     check against row, check against column, and check against block
        //     Any values that are 'solved' in the same row/column/block can
        //     now be eliminated for the cell being considered.
        for (int i = 0; i < 9; i++) {
            for (int j = 0; j < 9; j++) {
                if (!grid.isSolved(i, j)) {
                    testColumnForCell(i, j);

                    testRowForCell(i, j);

                    testBlockForCell(i, j);
                }
            }
        }
    }

    private void pairsOnLine() {
        // If two cells in a line share the same two values as their
        // only possible solutions, then these two values can be removed
        // from the other cells on that line

        for (int i = 0; i < 9; i++) {
            // Considering row i, check all column entries
            for (int j = 0; j < 8; j++) {
                int[] possibleforij = grid.getChoices(i, j);
                if (possibleforij.length != 2) {
                    continue;
                }

                // Check remaining cells in the line
                for (int k = j + 1; k < 9; k++) {
                    int[] possiblesforik = grid.getChoices(i, k);
                    if (possiblesforik.length != 2) {
                        continue;
                    }
                    if (Arrays.equals(possibleforij, possiblesforik)) {
                        // Cells [i][j] and [i][k] have the same two possibles
                        // so eliminate theses possibles from the other cells
                        // in row
                        for (int l = 0; l < 9; l++) {
                            if ((l != j) && (l != k)) {
                                grid.setFalse(i, l, possiblesforik[0]);
                                grid.setFalse(i, l, possiblesforik[1]);
                            }
                        }
                    }
                }
            }
        }
    }

    private void pairsInColumn() {
        // If two cells in a column share the same two values as their
        // only possible solutions, then these two values can be removed
        // from the other cells in that column    

        for (int j = 0; j < 9; j++) {
            // Checking column j, check all row entries
            for (int i = 0; i < 8; i++) {
                int[] possiblesforij = grid.getChoices(i, j);
                if (possiblesforij.length != 2) {
                    continue;
                }

                // Check the rest of the cells further down the column
                for (int k = i + 1; k < 9; k++) {
                    int[] possiblesforkj = grid.getChoices(k, j);
                    if (possiblesforkj.length != 2) {
                        continue;
                    }

                    if (Arrays.equals(possiblesforij, possiblesforkj)) {
                        for (int l = 0; l < 9; l++) {
                            if ((l != i) && (l != k)) {
                                grid.setFalse(l, j, possiblesforij[0]);
                                grid.setFalse(l, j, possiblesforij[1]);
                            }
                        }
                    }
                }
            }
        }
    }

    private void pairsInBlock() {
        // If two cells in a block share the same two values as their
        // only possible solutions, then these two values can be removed
        // from the other cells in that block

        for (int g = 0; g < 3; g++) {
            for (int h = 0; h < 3; h++) {

                // Looking at block g,h - first row 3*g, first column 3*h
                int gOffset = 3 * g;
                int hOffset = 3 * h;
                for (int i = 0; i < 8; i++) {
                    int rowi = i / 3;
                    int coli = i % 3;
                    // CellValue cv1 = values[rowi + gOffset][coli + hOffset];
                    int[] possibles1 = grid.getChoices(rowi + gOffset, coli + hOffset);
                    if (possibles1.length != 2) {
                        continue;
                    }

                    // So have one cell in current block, compare with remaining cells
                    // block viewed as   0,1,2
                    //                   3,4,5
                    //                   6,7,8

                    for (int j = i + 1; j < 9; j++) {
                        int rowj = j / 3;
                        int colj = j % 3;
                        //  CellValue cv2 = values[rowj + gOffset][colj + hOffset];
                        int[] possibles2 = grid.getChoices(rowj + gOffset, colj + hOffset);
                        if (possibles2.length != 2) {
                            continue;
                        }

                        if (!Arrays.equals(possibles1, possibles2)) {
                            continue;
                        }

                        // OK, two cells in this block share the same 2 possible values
                        // as their only choice
                        // So eliminate those 2 values from the other cells in block

                        for (int k = 0; k < 9; k++) {
                            if (k == i) {
                                continue;
                            }
                            if (k == j) {
                                continue;
                            }
                            int rowk = k / 3;
                            int colk = k % 3;
                            //CellValue cv3 = values[rowk + gOffset][colk + hOffset];
                            grid.setFalse(rowk + gOffset, colk + hOffset, possibles1[0]);
                            grid.setFalse(rowk + gOffset, colk + hOffset, possibles1[1]);

                        }

                    }
                }

            }
        }
    }

    private void pairs() {
        // Checking for pairs in rows, columns, and blocks
        pairsOnLine();
        pairsInColumn();
        pairsInBlock();

        eliminateUsedValues();
    }

    private void checkColumnTripletInRow(int col1, int col2, int col3, int row) {

        int count1 = grid.getNumChoices(row, col1);
        int count2 = grid.getNumChoices(row, col2);
        int count3 = grid.getNumChoices(row, col3);

        boolean possibleTriplet =
                ((count1 == 2) || (count1 == 3))
                && ((count2 == 2) || (count2 == 3))
                && ((count3 == 2) || (count3 == 3));

        if (!possibleTriplet) {
            return;
        }

        boolean[] possvalues = new boolean[10]; // Ignore [0]
        for (int i = 1; i < 10; i++) {
            possvalues[i] = false;
        }

        int valuecount = 0;

        for (int val = 1; val < 10; val++) {
            if (grid.couldBeThisValue(row, col1, val) && !possvalues[val]) {
                possvalues[val] = true;
                valuecount++;
            }
            if (grid.couldBeThisValue(row, col2, val) && !possvalues[val]) {
                possvalues[val] = true;
                valuecount++;
            }
            if (grid.couldBeThisValue(row, col3, val) && !possvalues[val]) {
                possvalues[val] = true;
                valuecount++;
            }
        }

        if (valuecount == 3) {
            for (int val = 1; val < 10; val++) {
                if (possvalues[val]) {
                    for (int m = 0; m < 9; m++) {
                        if ((m != col1) && (m != col2) && (m != col3)) {
                            grid.setFalse(row, m, val);
                            grid.setFalse(row, m, val);
                            grid.setFalse(row, m, val);
                        }
                    }
                }
            }
        }




    }

    private void checkForTripletsInRow(int row) {
        // Check all combinations of 3 columns in this row
        for (int j = 0; j < 7; j++) {
            if (grid.isSolved(row, j)) {
                continue;
            }
            for (int k = j + 1; k < 8; k++) {
                if (grid.isSolved(row, k)) {
                    continue;
                }
                for (int l = k + 1; l < 9; l++) {
                    if (grid.isSolved(row, l)) {
                        continue;
                    }
                    checkColumnTripletInRow(j, k, l, row);
                }
            }
        }



    }

    private void rowTriplets() {
        /*
         *  Consider one row -
         *     ? ? (2/7) | ? ? (2/5/7) | (2/5) ? 
         * 
         *   Cannot tell where 2, 5, 7 will get placed - but know that these
         *   three values must go in these cells in this row.
         *   Therefore cannot go in other columns in this row.
         * 
         *   For each row, consider all possible sets of 3 columns
         *   Work out all the allowed values for those 3 columns
         *   If exactly 3 allowed values for that set, remove those value
         *   from other columns in this row.
         * 
         */
        for (int row = 0; row < 9; row++) {
            checkForTripletsInRow(row);
        }
    }

    private void checkRowTripletInColumn(int row1, int row2, int row3, int col) {

        int count1 = grid.getNumChoices(row1, col);
        int count2 = grid.getNumChoices(row2, col);
        int count3 = grid.getNumChoices(row3, col);

        boolean possibleTriplet =
                ((count1 == 2) || (count1 == 3))
                && ((count2 == 2) || (count2 == 3))
                && ((count3 == 2) || (count3 == 3));

        if (!possibleTriplet) {
            return;
        }

        boolean[] possvalues = new boolean[10]; // Ignore [0]
        for (int i = 1; i < 10; i++) {
            possvalues[i] = false;
        }

        int valuecount = 0;

        for (int val = 1; val < 10; val++) {
            if (grid.couldBeThisValue(row1, col, val) && !possvalues[val]) {
                possvalues[val] = true;
                valuecount++;
            }
            if (grid.couldBeThisValue(row2, col, val) && !possvalues[val]) {
                possvalues[val] = true;
                valuecount++;
            }
            if (grid.couldBeThisValue(row3, col, val) && !possvalues[val]) {
                possvalues[val] = true;
                valuecount++;
            }
        }

        if (valuecount == 3) {
            for (int val = 1; val < 10; val++) {
                if (possvalues[val]) {
                    for (int m = 0; m < 9; m++) {
                        if ((m != row1) && (m != row2) && (m != row3)) {
                            grid.setFalse(m, col, val);
                            grid.setFalse(m, col, val);
                            grid.setFalse(m, col, val);
                        }
                    }
                }
            }
        }




    }

    private void checkForTripletsInColumn(int col) {
        // Check all combinations of 3 rows in this column
        for (int j = 0; j < 7; j++) {
            if (grid.isSolved(j, col)) {
                continue;
            }
            for (int k = j + 1; k < 8; k++) {
                if (grid.isSolved(k, col)) {
                    continue;
                }
                for (int l = k + 1; l < 9; l++) {
                    if (grid.isSolved(l, col)) {
                        continue;
                    }
                    checkRowTripletInColumn(j, k, l, col);
                }
            }
        }



    }

    private void columnTriplets() {
        /*
         *  Consider one row -
         *     ? 
         *     ?
         *     (2/7)
         *     ----
         *     ?
         *     ?
         *     (2/5/7)
         *     ---
         *     (2/5)
         *     ?
         *     ?
         * 
         *   Cannot tell where 2, 5, 7 will get placed - but know that these
         *   three values must go in these cells in this column.
         *   Therefore cannot go in other columns in this column.
         * 
         *   For each column, consider all possible sets of 3 rows
         *   Work out all the allowed values for those 3 rows
         *   If exactly 3 allowed values for that set, remove those value
         *   from other rows in this column.
         * 
         */
        for (int col = 0; col < 9; col++) {
            checkForTripletsInColumn(col);
        }
    }

    private void checkPossibleTripletInBlock(int j, int k, int l, int g, int h) {



        int r = 3 * g + j % 3;
        int c = 3 * h + j / 3;
        int r1 = 3 * g + k % 3;
        int c1 = 3 * h + k / 3;
        int r2 = 3 * g + l % 3;
        int c2 = 3 * h + l / 3;
        int count1 = grid.getNumChoices(r, c);
        int count2 = grid.getNumChoices(r1, c1);
        int count3 = grid.getNumChoices(r2, c2);

        boolean possibleTriplet =
                ((count1 == 2) || (count1 == 3))
                && ((count2 == 2) || (count2 == 3))
                && ((count3 == 2) || (count3 == 3));

        if (!possibleTriplet) {
            return;
        }

        boolean[] possvalues = new boolean[10]; // Ignore [0]
        for (int i = 1; i < 10; i++) {
            possvalues[i] = false;
        }

        int valuecount = 0;

        for (int val = 1; val < 10; val++) {
            if (grid.couldBeThisValue(r, c, val) && !possvalues[val]) {
                possvalues[val] = true;
                valuecount++;
            }
            if (grid.couldBeThisValue(r1, c1, val) && !possvalues[val]) {
                possvalues[val] = true;
                valuecount++;
            }
            if (grid.couldBeThisValue(r2, c2, val) && !possvalues[val]) {
                possvalues[val] = true;
                valuecount++;
            }
        }

        if (valuecount == 3) {
            for (int val = 1; val < 10; val++) {
                if (possvalues[val]) {
                    for (int m = 0; m < 9; m++) {
                        if ((m != j) && (m != k) && (m != l)) {
                            int row = 3 * g + m % 3;
                            int col = 3 * h + m / 3;

                            grid.setFalse(row, col, val);
                            grid.setFalse(row, col, val);
                            grid.setFalse(row, col, val);
                        }
                    }
                }
            }

        }

    }

    private void checkBlockForTriplet(int g, int h) {
        // values[3 * g + l % 3][3 * h + l / 3]
        for (int j = 0; j < 7; j++) {
            int r = 3 * g + j % 3;
            int c = 3 * h + j / 3;
            if (grid.isSolved(r, c)) {
                continue;
            }
            for (int k = j + 1; k < 8; k++) {
                int r1 = 3 * g + k % 3;
                int c1 = 3 * h + k / 3;
                if (grid.isSolved(r1, c1)) {
                    continue;
                }
                for (int l = k + 1; l < 9; l++) {
                    int r2 = 3 * g + l % 3;
                    int c2 = 3 * h + l / 3;
                    if (grid.isSolved(r2, c2)) {
                        continue;
                    }
                    checkPossibleTripletInBlock(j, k, l, g, h);
                }
            }
        }

    }

    private void blockTriplets() {
        for (int g = 0; g < 3; g++) {
            for (int h = 0; h < 3; h++) {
                checkBlockForTriplet(g, h);
            }
        }
    }

    private void triplets() {


        rowTriplets();
        columnTriplets();
        blockTriplets();

        eliminateUsedValues();
    }

    private void onlyPositionInRow(int row) {
        // For each value 1...9 check to see if there is only
        // one cell in this row that could possibly have that value

        boolean updated = false;
        for (int val = 1; val <= 9; val++) {

            // Check whether the value 'val' is already known for this row,
            // in which case go on to next value.
            // Also check if only one spot left - in which case claim it.
            boolean alreadydealtwith = false;
            int numberopenings = 0;
            int lastopening = -1;

            for (int j = 0; j < 9; j++) {
                if (grid.isThisValue(row, j, val)) {
                    alreadydealtwith = true;
                    break;
                } else if (grid.couldBeThisValue(row, j, val)) {
                    numberopenings++;
                    lastopening = j;
                }
            }

            if (alreadydealtwith) {
                continue;   // Check for another number
            }

            if (numberopenings == 1) {
                // Only one spot for digit 'val'

                grid.setValue(row, lastopening, val);

                grid.flagSimplified(row, lastopening, val);
                updated = true;

            }
        }
        if (updated) {
            eliminateUsedValues();
        }
    }

    private void onlyPositionInRows() {
        // Check all the rows in turn to see if any has a single
        // opening for a specific value
        for (int i = 0; i < 9; i++) {
            onlyPositionInRow(i);
        }
    }

    private void onlyPositionInColumn(int col) {
        // For each value 1...9 check to see if there is only
        // one cell in this col that could possibly have that value

        boolean updated = false;
        for (int val = 1; val <= 9; val++) {


            // Check whether the value 'val' is already known for this col
            boolean alreadydealtwith = false;

            int numberopenings = 0;
            int lastopening = -1;

            for (int i = 0; i < 9; i++) {
                if (grid.isThisValue(i, col, val)) {
                    alreadydealtwith = true;
                    break;
                } else if (grid.couldBeThisValue(i, col, val)) {
                    numberopenings++;
                    lastopening = i;
                }
            }
            if (alreadydealtwith) {
                continue;
            }


            if (numberopenings == 1) {
                // Only one spot for digit 'val'

                // Ah - learnt something new
                //values[lastopening][col] = new CellValue(val);
                grid.setValue(lastopening, col, val);
                grid.flagSimplified(lastopening, col, val);
                updated = true;

            }
        }
        if (updated) {
            eliminateUsedValues();
        }
    }

    private void onlyPositionInColumns() {
        // Check all the columns in turn to see if any has a single
        // opening for a specific value
        for (int i = 0; i < 9; i++) {
            onlyPositionInColumn(i);
        }
    }

    private void onlyPositionInBlock(int gOffset, int hOffset) {
        // For each value 1...9 check to see if there is only
        // one cell in this block that could possibly have that value
        boolean updated = false;
        for (int val = 1; val <= 9; val++) {


            // Check whether the value 'val' is already known for this block
            boolean alreadydealtwith = false;
            for (int x = 0; x < 9; x++) {
                int xrow = x / 3;
                int xcol = x % 3;

                int r = xrow + gOffset;
                int c = xcol + hOffset;
                if (grid.isThisValue(r, c, val)) {
                    alreadydealtwith = true;
                    break;
                }
            }

            if (alreadydealtwith) {
                continue;
            }

            int numberopenings = 0;
            CellId id = null;

            for (int k = 0; k < 9; k++) {
                int krow = k / 3;
                int kcol = k % 3;

                int r = krow + gOffset;
                int c = kcol + hOffset;

                if (grid.couldBeThisValue(r, c, val)) {
                    if (numberopenings == 0) {
                        id = new CellId(r, c);
                    }
                    numberopenings++;
                }
            }
            if (numberopenings == 1) {
                int i = id.getI();
                int j = id.getJ();

                grid.setValue(i, j, val);
                grid.flagSimplified(i, j, val);
                updated = true;
            }
        }
        if (updated) {
            eliminateUsedValues();
        }
    }

    private void onlyPositionInBlocks() {
        for (int g = 0; g < 3; g++) {
            for (int h = 0; h < 3; h++) {
                int gOffset = 3 * g;
                int hOffset = 3 * h;
                onlyPositionInBlock(gOffset, hOffset);
            }
        }
    }

    private void onlyOnePosition() {

        onlyPositionInRows();
        onlyPositionInColumns();
        onlyPositionInBlocks();

    }

    private boolean rowInclusion(int val, int row, int col) {
        // Checking 3 columns (in same block) of given row
        // Can value be placed in any of them?
        return (grid.couldBeThisValue(row, col, val)
                || grid.couldBeThisValue(row, col + 1, val)
                || grid.couldBeThisValue(row, col + 2, val));
    }

    private boolean columnInclusion(int val, int row, int col) {
        // Checking 3 rows (in same block) of given column
        // Can value be placed in any of them?        
        return (grid.couldBeThisValue(row, col, val)
                || grid.couldBeThisValue(row + 1, col, val)
                || grid.couldBeThisValue(row + 2, col, val));
    }

    private void clearValueFromRowInNeighboringBlocks(int val, int row, int ownblock) {
        for (int col = 0; col < 9; col++) {
            if ((col / 3) == ownblock) {
                continue;
            }

            grid.setFalse(row, col, val);
        }
    }

    private void clearValueFromColumnInNeighboringBlocks(int val, int col, int ownblock) {
        for (int row = 0; row < 9; row++) {
            if ((row / 3) == ownblock) {
                continue;
            }

            grid.setFalse(row, col, val);
        }
    }

    private void checkValInBlock(int val, int g, int h) {

        int gOffset = 3 * g;
        int hOffset = 3 * h;

        boolean alreadyPlaced = false;

        for (int k = 0; k < 9; k++) {
            int r = gOffset + (k / 3);
            int c = hOffset + (k % 3);
            // CellValue cv = values[r][c];
            if (grid.isThisValue(r, c, val)) {
                alreadyPlaced = true;
            }
        }
        if (alreadyPlaced) {
            return;
        }

        boolean row1Excluded = !rowInclusion(val, gOffset, hOffset);
        boolean row2Excluded = !rowInclusion(val, gOffset + 1, hOffset);
        boolean row3Excluded = !rowInclusion(val, gOffset + 2, hOffset);

        boolean col1Excluded = !columnInclusion(val, gOffset, hOffset);
        boolean col2Excluded = !columnInclusion(val, gOffset, hOffset + 1);
        boolean col3Excluded = !columnInclusion(val, gOffset, hOffset + 2);

        if (row1Excluded && row2Excluded) {
            clearValueFromRowInNeighboringBlocks(val, gOffset + 2, h);
        }
        if (row1Excluded && row3Excluded) {
            clearValueFromRowInNeighboringBlocks(val, gOffset + 1, h);
        }
        if (row2Excluded && row3Excluded) {
            clearValueFromRowInNeighboringBlocks(val, gOffset, h);
        }

        if (col1Excluded && col2Excluded) {
            clearValueFromColumnInNeighboringBlocks(val, hOffset + 2, g);
        }
        if (col1Excluded && col3Excluded) {
            clearValueFromColumnInNeighboringBlocks(val, hOffset + 1, g);
        }
        if (col2Excluded && col3Excluded) {
            clearValueFromColumnInNeighboringBlocks(val, hOffset, g);
        }

    }

    private void checkBlock(int g, int h) {
        // See explanation in main routine 'inBlockRowOrColumn'
        // This function loops through digits 1..9 for the current block

        for (int val = 1; val <= 9; val++) {
            checkValInBlock(val, g, h);
        }
    }

    private void inBlockRowOrColumn() {
        /*
         *    Consider each 3x3 block in turn:
         *        Consider each digit 1...9 in turn.
         * 
         *            Determine first whether the position for that digit is
         *            already known for the block - if it is, then there is
         *            no more to do.
         * 
         *            But if not solved for that digit in this block, examine
         *            possible positions:
         * 
         *            Consider this case
         * 
         *        ? ? ? | X X X | ? ? ?
         *        ? ? ? | X X X | ? ? ?
         *        # # # | * * * | # # #
         * 
         *      Working on the middle block, have determined that digit 'val'
         *      hasn't yet been "solved" in this block and also that it cannot
         *      go in the cells in the top two rows (marked as X).
         *      Which means it must be on the bottom row of this block!
         *            (marked as *)
         *      Which means that it cannot be on the bottom row of the block
         *      to left or right - so remove it as a possibility in those cells.
         *            (marked as #)
         * 
         *      Repeat this:
         *          1) is it excluded from 1st & 2nd row?  fix up 3rd row.
         *          2) is it excluded from 1st & 3rd row?  fix up 2nd row.
         *          3) is it excluded from 2nd & 3rd row?  fix up 1st row.
         *          4) is it excluded from 1st & 2nd column?  fix up 3rd column.
         *          5) is it excluded from 1st & 3rd column? fix up 2nd column.
         *          6) is it excluded from 2nd & 3rd column? fix up 1st column.
         */
        for (int g = 0; g < 3; g++) {
            for (int h = 0; h < 3; h++) {
                checkBlock(g, h);
            }
        }

        eliminateUsedValues();

    }

    private void clearValFromRow(int val, int row, int blknum, int colstart) {
        for (int r = 0; r < 3; r++) {
            if (r == row) {
                continue;
            }
            // val must be in columns colstart ... (colstart+2) of "row"
            // remove it as a possiblity for these columns in other two
            // rows of this block
            int arow = r + 3 * blknum; // Actual row
            for (int k = colstart; k < colstart + 3; k++) {
                grid.setFalse(arow, k, val);
            }
        }
    }

    private void checkValueInRowInBlock(int val, int row, int blknum) {
        boolean[] p = new boolean[9];
        int arow = row + 3 * blknum; // actual row in 9x9 array
        for (int c = 0; c < 9; c++) {
            //CellValue cv = values[arow][c];
            // If position for 'val' is known, no more work to be done
            // (implications of value being in that cell will already
            // have been sorted out and used to update possibilities for
            // other all other cells)
            if (grid.isSolved(arow, c) && (grid.getValue(arow, c) == val)) {
                return;
            }
            p[c] = grid.couldBeThisValue(arow, c, val);
        }
        // Check 3 blocks along the row
        boolean notIn1 =
                !p[0] && !p[1] && !p[2];
        boolean notIn2 =
                !p[3] && !p[4] && !p[5];
        boolean notIn3 =
                !p[6] && !p[7] && !p[8];

        if (notIn1 && notIn2) {
            clearValFromRow(val, row, blknum, 6);
        }
        if (notIn1 && notIn3) {
            clearValFromRow(val, row, blknum, 3);
        }
        if (notIn2 && notIn3) {
            clearValFromRow(val, row, blknum, 0);
        }

    }

    private void checkRowInBlock(int row, int blknum) {
        for (int val = 1; val < 10; val++) {
            checkValueInRowInBlock(val, row, blknum);
        }
    }

    private void checkRowsInBlock(int blknum) {

        for (int row = 0; row < 3; row++) {
            checkRowInBlock(row, blknum);
        }

    }

    private void checkRowsByBlock() {
        for (int g = 0; g < 3; g++) {
            checkRowsInBlock(g);
        }
    }

    private void clearValFromColumn(int val, int col, int blknum, int rowstart) {
        for (int c = 0; c < 3; c++) {
            if (c == col) {
                continue;
            }
            // val must be in columns colstart ... (colstart+2) of "row"
            // remove it as a possiblity for these columns in other two
            // rows of this block
            int acol = c + 3 * blknum; // Actual column
            for (int r = rowstart; r < rowstart + 3; r++) {
                grid.setFalse(r, acol, val);
            }
        }
    }

    private void checkValueInColumnInBlock(int val, int col, int blknum) {
        boolean[] p = new boolean[9];
        int acol = col + 3 * blknum; // actual column  in 9x9 array
        for (int r = 0; r < 9; r++) {
            //CellValue cv = values[r][acol];
            // If position for 'val' is known, no more work to be done
            // (implications of value being in that cell will already
            // have been sorted out and used to update possibilities for
            // other all other cells)
            if (grid.isSolved(r, acol) && (grid.getValue(r, acol) == val)) {
                return;
            }
            p[r] = grid.couldBeThisValue(r, acol, val);
        }
        // Check 3 blocks along the row
        boolean notIn1 =
                !p[0] && !p[1] && !p[2];
        boolean notIn2 =
                !p[3] && !p[4] && !p[5];
        boolean notIn3 =
                !p[6] && !p[7] && !p[8];

        if (notIn1 && notIn2) {
            clearValFromColumn(val, col, blknum, 6);
        }
        if (notIn1 && notIn3) {
            clearValFromColumn(val, col, blknum, 3);
        }
        if (notIn2 && notIn3) {
            clearValFromColumn(val, col, blknum, 0);
        }

    }

    private void checkColumnInBlock(int col, int blknum) {
        for (int val = 1; val < 10; val++) {
            checkValueInColumnInBlock(val, col, blknum);
        }
    }

    private void checkColumnsInBlock(int blknum) {

        for (int col = 0; col < 3; col++) {
            checkColumnInBlock(col, blknum);
        }

    }

    private void checkColumnsByBlock() {
        for (int g = 0; g < 3; g++) {
            checkColumnsInBlock(g);
        }
    }

    private void inRowOrColumn() {
        /*
         *    Similar, but slightly simpler, than inBlockRowOrColumn
         *       
         *   Rows first 
         * 
         *     Suppose found a row where placement of a value, e.g. 3, is not
         *     yet resolved
         * 
         *     But find that in this row it cannot be in  columns 0...6 (!)
         * 
         *      x x x | x x x  | x x x 
         *      x x x | x x x  | x x x 
         *      x x x | x x x  | x x x  
         *      -------------------------
         *      x x x | x x x  | @ @ @
         *      ! ! ! | ! ! !  | ? ? ?   <---- talking about this row
         *      x x x | x x x  | @ @ @ 
         *      -------------------------
         *      x x x | x x x  | x x x 
         *      x x x | x x x  | x x x  
         *      x x x | x x x  | x x x 
         *      
         *  Value '3' must be in one of those positions marked by ?
         *  So it cannot be in any of the positions marke by @
         * 
         * ----
         * Columns
         *    Similar
         * 
         * 
         */

        checkRowsByBlock();
        checkColumnsByBlock();
    }

    private void checkRowsAndColumns() {
        checkRowsByBlock();
        checkColumnsByBlock();
        eliminateUsedValues();

    }

    public Sudoku copy() {
        Sudoku copy = new Sudoku();

        CellArray grid2 = copy.getCellArray();
        for (int i = 0; i < 9; i++) {
            for (int j = 0; j < 9; j++) {
                if (grid.isSolved(i, j)) {
                    grid2.setValue(i, j, grid.getValue(i, j));
                }
            }
        }
        return copy;
    }
}
