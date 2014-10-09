package sudokugenerator;

/*

 Based on code by Eric Frankenberg,
 eric.frankenberg@iut-tlse3.fr

 */
public class CellId {

    // Acts as an identifier for a cell in Sudoku grid
    private int i, j;

    public int getI() {
        return i;
    }

    public void setI(int i) {
        this.i = i;
    }

    public int getJ() {
        return j;
    }

    public void setJ(int j) {
        this.j = j;
    }

    public CellId(int i, int j) {
        this.i = i;
        this.j = j;
    }
}
