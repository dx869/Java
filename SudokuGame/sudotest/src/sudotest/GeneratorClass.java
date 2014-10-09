/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package sudokugenerator;

import java.util.Random;
import java.util.ArrayList;

/**
 *
 * @author nabg
 */
public class GeneratorClass {

    // Need to use controlled random number, so replace Math.random()
    // with calls to initialized random number generator myRandom.nextDouble()
    private Random myRandom;

    public GeneratorClass() {
        myRandom = new Random();
    }

    public void reseedRandom(int seed) {
        myRandom = new Random(seed);
    }

    public Sudoku createValidSolution() {
        Sudoku sudo;
        int pot[];
        CellArray agrid;
        // Kind of brute force
        //   Create a new empty puzzle
        //   Work through array -
        //     change cell to randomly chosen value 1-9, compute any consequences
        //         (setting a particular cell to e.g. '3' removes 3 as a possiblity
        //         for any other cell in same row, andy other cell in same column, or
        //         any other cell in same block)
        //  Check the final result - is it valid? (all rows contain each digit, all
        //  columns contain each digit, all block contain each digit)
        //
        // Not valid? well go back and try again!
        do {
            sudo = new Sudoku();
            agrid = sudo.getCellArray();

            for (int i = 0; i < 9; i++) {
                for (int j = 0; j < 9; j++) {
                    if (!agrid.isSolved(i, j)) {
                        pot = agrid.getChoices(i, j);
                        if(pot.length==0) continue;  // Bad earlier choices! Nothing left for this cell.
                        int randompick = (int) (pot.length * myRandom.nextDouble());
                        agrid.genSetValue(i, j, pot[randompick]);


                        // "sudo.solve"
                        // Works out all implications of putting the chosen
                        // value in specified cell -
                        //   same value cannot go elsewhere in that row, or column
                        //   etc - this gets handled by updates of the choices
                        //   left for the other cells.

                        // All the same, it's expensive; makes puzzle generation
                        // quite slow.
                        // sudo.solve();
                        
                        // All that is really needed is to reduce remaining choices
                        // by eliminating those that don't match the cell values
                        // already selected.  Part is now done in "genSetValue"
                        // do a little more -
                        sudo.solveAverage();
                    }
                }
            }
        } while (!sudo.isValid());

        return sudo;

    }

    private Sudoku makePuzzleFromSolution(Sudoku soln, int level) {

        Sudoku puzzle;
        puzzle = soln.copy();
        CellArray agrid = puzzle.getCellArray();

        // Now create a puzzle by removing some cell values

        // First create a list of "position" structs - these are simply
        // cell identifiers with row and column
        // Will be randomly picking entries from this list - i.e. randomly
        // picking the cells that are to be cleared to make the puzzle
        ArrayList<CellId> cellsList = new ArrayList();

        // Fill the list with all cells - when start, all a candidates for
        // being made "unknowns".
        for (int i = 0; i < 9; i++) {
            for (int j = 0; j < 9; j++) {
                CellId pos = new CellId(i, j);
                cellsList.add(pos);
            }
        }
        //
        // Now attempt to create the puzzle
        // Again pretty much brute force

        do {
            Sudoku sudo2;
            int randompick = (int) (cellsList.size() * myRandom.nextDouble());
            int i = cellsList.get(randompick).getI();
            int j = cellsList.get(randompick).getJ();
            // Save current value in this cell (solution value)
            int val = agrid.getValue(i, j);
            // Set cell to "unknown"
            agrid.setUnknown(i, j);
            // Try to solve this puzzle - using rules as appropriate to level
            sudo2 = puzzle.copy();
            sudo2.solveForLevel(level);
            // Did solution process yield a valid solution?
            if (!sudo2.isValid()) {
                // No, so put back the value that was just taken out
                agrid.setValue(i, j, val);
            }
            // Don't consider that cell any more
            cellsList.remove(randompick);
        } while (cellsList.size() > 0);
        return puzzle;
    }

    
    public SudokuPuzzle generate(int level) {
        SudokuPuzzle result = new SudokuPuzzle();
        Sudoku soln, puzzle;
        //       System.out.println("Generating puzzle @ level " + level);
        //       System.out.println("Repeatedly generate puzzles - print * - until difficulty level matched");
        int plevel = 0;
        do {
            soln = createValidSolution();
            puzzle = makePuzzleFromSolution(soln, level);
            result.setPuzl(puzzle);
            result.setSoln(soln);
           // System.out.print("*");
            plevel = getLevel(puzzle);
           // System.out.print(plevel);
            if(plevel<0) {
                 System.out.println("Ooops - I seem to have generated a problem that I can't solve myself!");
                 System.out.println("Puzzle ");
                 System.out.println(result.getPuzlString());
                 System.out.println("Where should have found this solution:");
                 System.out.println(result.getSolnString());
                 System.exit(1);
            }
           
        } while (plevel != level);
        //System.out.println();
        return result;
    }

    public SudokuPuzzle generateDevilishlyHard() {
        return generate(4);
    }

    public SudokuPuzzle generateDifficult() {
        return generate(3);
    }

    public SudokuPuzzle generateAverage() {
        return generate(2);
    }

    public SudokuPuzzle generateEasy() {
        return generate(1);
    }

    public SudokuPuzzle generateVeryEasy() {
        return generate(0);
    }

    public int getLevel(Sudoku sudo) {

        Sudoku sudo2;

        sudo2 = sudo.copy();
        sudo2.solveVeryEasy();  // Try to solve as a very easy problem!
        if (sudo2.isValid()) {
            return 0; // It was very easy.
        }
        sudo2 = sudo.copy();
        sudo2.solveEasy(); // Try to solve it as an easy problem
        if (sudo2.isValid()) {
            return 1; // It was easy
        }
        sudo2 = sudo.copy();
        sudo2.solveAverage();  // Try to solve as a middling problem
        if (sudo2.isValid()) {
            return 2; // Middling
        }
        sudo2 = sudo.copy();
        sudo2.solveDifficult();
        if (sudo2.isValid()) {
            return 3; // Difficult
        }

        // Guess it's diabolic
        // No real need to invoke the solve() ! as generation process
        // will have created a problem with a solution
        
        // All the same, try it
        sudo2 = sudo.copy();
        sudo2.solve();
        if(sudo2.isValid()) {
            return 4;
        }
        return -1;
       
    }
}
