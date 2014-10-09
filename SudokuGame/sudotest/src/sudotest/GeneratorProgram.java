/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package sudokugenerator;

import java.util.Random;
import java.util.Scanner;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author nabg
 */
public class GeneratorProgram {

    public static void printsudo(Sudoku sudo) {
        CellArray val = sudo.getCellArray();
        for (int i = 0; i < 9; i++) {
            for (int j = 0; j < 9; j++) {
                System.out.print(val.getValue(i, j) + " ");
                if ((j == 2) || (j == 5)) {
                    System.out.print("| ");
                }
            }
            System.out.println();
            if ((i == 2) || (i == 5)) {
                System.out.println("------+------+------");
            }

        }
    }

    private static void basicTest() {
        GeneratorClass sgen = new GeneratorClass();

        SudokuPuzzle sudo;
        sgen.reseedRandom(12345);  // Reset seed if need to check that same puzzles generated
        System.out.println("Generate a diabolic puzzle");
        sudo = sgen.generateDevilishlyHard();
        System.out.println("Puzzle:");
        printsudo(sudo.getPuzl());
        System.out.println("Solution:");
        printsudo(sudo.getSoln());


        System.out.println("Generate a difficult puzzle");
        sgen.reseedRandom(12345);  // Reset seed if need to check that same puzzles generated
        sudo = sgen.generateDifficult();
        System.out.println("Puzzle:");
        printsudo(sudo.getPuzl());
        System.out.println("Solution:");
        printsudo(sudo.getSoln());


        System.out.println("Generate a medum puzzle");
        sgen.reseedRandom(12345);  // Reset seed if need to check that same puzzles generated
        sudo = sgen.generateAverage();
        System.out.println("Puzzle:");
        printsudo(sudo.getPuzl());
        System.out.println("Solution:");
        printsudo(sudo.getSoln());

        System.out.println("Generate an easy puzzle");
        sgen.reseedRandom(12345);  // Reset seed if need to check that same puzzles generated
        sudo = sgen.generateEasy();
        System.out.println("Puzzle:");
        printsudo(sudo.getPuzl());
        System.out.println("Solution:");
        printsudo(sudo.getSoln());

        System.out.println("Generate a very easy puzzle");

        sgen.reseedRandom(12345);  // Reset seed if need to check that same puzzles generated
        sudo = sgen.generateVeryEasy();
        System.out.println("Puzzle:");
        printsudo(sudo.getPuzl());
        System.out.println("Solution:");
        printsudo(sudo.getSoln());

    }

    private static void timeTest() {
        System.out.println("Measuring time to generate 50 devilishly hard puzzles");
        long tstart = System.currentTimeMillis();
        Random seeder = new Random(666);
        GeneratorClass sgen = new GeneratorClass();
        SudokuPuzzle sudo;
        // Want to generate a random assortment of puzzles - but the same set
        // every time so that timings are more significant.
        for (int i = 0; i < 50; i++) {
            int seed = (int) (seeder.nextDouble() * 1000.0);
            sgen.reseedRandom(seed);
            sudo = sgen.generateDevilishlyHard();
            System.out.println("P : " + sudo.getPuzlString());
            System.out.println("S : " + sudo.getSolnString());
            System.out.println();
        }
        long tstop = System.currentTimeMillis();
        long diff = tstop - tstart;
        System.out.println("Time taken " + diff + "ms");
    }

    private static void choosePuzzles() {
        Scanner input = new Scanner(System.in);
        for (;;) {
            System.out.println("Pick difficulty level for you puzzle");
            System.out.println("0 - very easy, ... , 4 - 'diabolical'");
            System.out.print("Level : ");
            if (!input.hasNextInt()) {
                break;
            }
            int lvl = input.nextInt();
            if ((lvl < 0) || (lvl > 5)) {
                System.out.println("Invalid request!");
                continue;
            }
            GeneratorClass sgen = new GeneratorClass();
            SudokuPuzzle sudo = null;
            switch (lvl) {
                case 0:
                    sudo = sgen.generateVeryEasy();
                    break;
                case 1:
                    sudo = sgen.generateEasy();
                    break;
                case 2:
                    sudo = sgen.generateAverage();
                    break;
                case 3:
                    sudo = sgen.generateDifficult();
                    break;
                case 4:
                    sudo = sgen.generateDevilishlyHard();
                    break;
            }
            System.out.println("Your puzzle : ");
            printsudo(sudo.getPuzl());
            System.out.println(sudo.getPuzlString());
            int sleepy = 1 + 10 * lvl;
            sleepy = sleepy * 1000;
            try {
                Thread.sleep(sleepy);
            } catch (InterruptedException ex) {
                // Do nothing!;
            }
            input.nextLine(); // Discard new line after last entry
            System.out.println("Do you want the solution?");
            String str = input.nextLine().trim();
            str = str.toLowerCase();
            if (str.startsWith("y")) {
                System.out.println("I'll take that as yes");
                System.out.println("The solution to that puzzle is ");

                printsudo(sudo.getSoln());
            }

        }
        System.out.println("Had enough? !");
    }

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        // basicTest();
        // timeTest();
        choosePuzzles();
    }
}
