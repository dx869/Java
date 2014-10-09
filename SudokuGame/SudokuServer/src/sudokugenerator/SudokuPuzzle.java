/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package sudokugenerator;

/**
 *
 * @author nabg
 */
public class SudokuPuzzle {

    private Sudoku puzl;
    private Sudoku soln;

    public SudokuPuzzle() {
    }

    public SudokuPuzzle(Sudoku puzl, Sudoku soln) {
        this.puzl = puzl;
        this.soln = soln;
    }

    public Sudoku getPuzl() {
        return puzl;
    }

    public void setPuzl(Sudoku puzl) {
        this.puzl = puzl;
    }

    public Sudoku getSoln() {
        return soln;
    }

    public void setSoln(Sudoku soln) {
        this.soln = soln;
    }
    
    private String getString(Sudoku s) {
        CellArray grid = s.getCellArray();
        StringBuilder sb = new StringBuilder();
        for(int row=0;row<9;row++)
            for(int col=0;col<9;col++) {
                int val = grid.getValue(row,col);
                sb.append(val);
            }
       return sb.toString();
    }
    
    public String getSolnString() {
        return getString(this.soln);
    }
    
    public String getPuzlString() {
        return getString(this.puzl);
    }
}
