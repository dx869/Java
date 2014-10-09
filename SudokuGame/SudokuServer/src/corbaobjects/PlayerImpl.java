/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package corbaobjects;

import Aphorism.PlayerPOA;
import SudokuPersistence.Games;
import SudokuPersistence.Moves;
import SudokuPersistence.Players;
import java.util.List;
import java.util.Random;
import javax.persistence.EntityManager;
import javax.persistence.Query;
import sudokugenerator.GeneratorClass;
import sudokugenerator.SudokuPuzzle;

import sudokuserver.SudokuServer;

/**
 *
 * @author rebecca
 */
public class PlayerImpl extends PlayerPOA {

    private String myName;
    private int gameid;
    private String aPassword;
    private String game;
    private Random r;
    private GeneratorClass sgen;
    private SudokuPuzzle sudo;
    private boolean loggedIn;
    private String savingCurrent;
    private int lev;
    private String puzzle;
    private String sol;

    public PlayerImpl(String name) {
        myName = name;
        System.out.println("PlayerImpl  starting" + name);
        loggedIn = false;
        r = new Random();
    }

    public PlayerImpl() {
        loggedIn = false;
        r = new Random();
    }

    public void loadData() {
    }

    //saving data when closed.

    public void saveData() {
        System.out.println("Saving data for " + myName);
        EntityManager em2 = SudokuServer.emf.createEntityManager();
        em2.getTransaction().begin();
        Query q = em2.createNamedQuery("Players.findByName");
        q.setParameter("name", myName);
        
        Players p=(Players) q.getSingleResult();
        p.setCurrentmove(game);
        em2.getTransaction().commit();
        em2.close();
    }

//handle player's  login
//1.check the password if not currect , renturn false
//2.if login success, set variable game and gameid 's value;
    @Override
    public boolean login(String password) {
        EntityManager em = SudokuServer.emf.createEntityManager();
        Query q = em.createNamedQuery("Players.findByName");
        q.setParameter("name", myName);
        Players player = (Players) q.getSingleResult();
        if (password.equals(player.getPassword())) {
            if (player.getGameid() == 0) {
                game = "0";
            } else {
                gameid = player.getGameid();
                game = player.getCurrentmove();

                findPuzzle();

            }

            loggedIn = true;
            return true;
        } else {
            return false;
        }

    }
// find the origanal puzzle
// set value to private var puzzle
//purpose is to give client to set the origanla number uneditable.

    public void findPuzzle() {
        EntityManager em = SudokuServer.emf.createEntityManager();
        Query q = em.createNamedQuery("Games.findByGameid");
        q.setParameter("gameid", gameid);
        Games g = (Games) q.getSingleResult();
        puzzle = g.getPuzzle();
        sol = g.getSolution();
    }
// this method will be called only when people just login
// the purpose for this method if to return a string to show palyer's old record
// in table : Games there are record with level and players name and if it is finithed.
//if player never finish any game, then return You have not completed any games.

    @Override
    public String getPuzzle() {
        return puzzle;
    }

    @Override
    public String gamerecord() {

        int[] record = new int[5];
        String message = "You have completed: ";
        record[0] = 0;
        record[1] = 0;
        record[2] = 0;
        record[3] = 0;
        record[4] = 0;
        EntityManager em = SudokuServer.emf.createEntityManager();
        Query q = em.createNamedQuery("Games.findByPlayer");
        q.setParameter("player", myName);
        List<Games> games = (List<Games>) q.getResultList();
        if (!game.isEmpty()) {
            for (Games g : games) {
                if (g.getFnish() == 1) {
                    record[g.getLevel()]++;
                }

            }

            if (record[0] != 0) {
                message = message + record[0] + " very easy games ";
            }
            if (record[1] != 0) {
                message = message + record[1] + "  easy games ";
            }
            if (record[2] != 0) {
                message = message + record[2] + " medium games ";

            }
            if (record[3] != 0) {
                message = message + record[3] + " hard games ";
            }
            if (record[4] != 0) {
                message = message + record[4] + " hell games ";

            }

            if (message.equals("You have completed: ")) {
                message = "You have not completed any game.";
            }
        } else {
            message = "You have not completed any game.";
        }
        return message;
    }
// just return game string

    @Override
    public String loadGame() {
        return game;
    }
//this will be used when player wants start a new game.
//call the method so a sudo will be settle.
// at the end of this method will call saveNewGame()
// just make the code more tidy, saveNewGame will handle the database
// some part will be changed for database.

    @Override
    public String create(short level) {
        lev = level;
        sgen = new GeneratorClass();
        sudo = null;
        switch (level) {
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

        String a = sudo.getPuzlString();

        saveNewGame();
        return a;
    }
    //will be called by create method 
    //take none para becuase all the var is updated.
    //just put information into database
    //1. int games, new row will be recoard.
    //2. in players , player's gameid  & currentmove will be updated

    public void saveNewGame() {
        EntityManager em = SudokuServer.emf.createEntityManager();
        puzzle = sudo.getPuzlString();
        sol = sudo.getSolnString();
        em.getTransaction().begin();
        Games g = new Games();
        g.setFnish(0);
        g.setPlayer(myName);
        g.setLevel(lev);
        g.setPuzzle(sudo.getPuzlString());
        g.setSolution(sudo.getSolnString());
        em.persist(g);
        em.getTransaction().commit();
        em.close();
        EntityManager em2 = SudokuServer.emf.createEntityManager();
        em2.getTransaction().begin();
        Query q = em2.createQuery("SELECT max(g.gameid) FROM Games g");
        gameid = (int) q.getSingleResult();
        Query q2 = em2.createNamedQuery("Players.findByName");
        q2.setParameter("name", myName);
        Players player = (Players) q2.getSingleResult();

        player.setGameid(gameid);
        player.setCurrentmove(sudo.getPuzlString());
        em2.getTransaction().commit();
        em2.close();

    }

    @Override
    public boolean recordMove(String string) {
        char a[] = string.toCharArray();
        int row = Integer.parseInt(a[0] + "");
        int col = Integer.parseInt(a[1] + "");

        StringBuffer str = new StringBuffer(game);
        str.setCharAt(row * 9 + col, a[2]);
        game = str.toString();

        EntityManager em = SudokuServer.emf.createEntityManager();
        em.getTransaction().begin();
        Moves move = new Moves();
        move.setGameid(gameid);
        move.setPlayer(myName);
        move.setMove(string);

        em.persist(move);
        em.getTransaction().commit();
        em.close();

        System.out.println("New move has been record: " + string);

        EntityManager em2 = SudokuServer.emf.createEntityManager();
        em2.getTransaction().begin();
        Query q = em2.createNamedQuery("Players.findByName");
        q.setParameter("name", myName);
        
        Players p=(Players) q.getSingleResult();
        p.setCurrentmove(game);
        em2.getTransaction().commit();
        em2.close();
        return true;
    }
//check the data
    //if the updated string is equal to solution then return true
    //at the same time remove all the moves for this game

    @Override
    public boolean check(String string) {
        System.out.println("Method check called by client");
        if (string.equals(sol)) {
            System.out.println("same as solution");
            EntityManager em = SudokuServer.emf.createEntityManager();
            em.getTransaction().begin();
            Query q = em.createNamedQuery("Moves.findByPlayer");
            q.setParameter("player", myName);
            List<Moves> moves = (List<Moves>) q.getResultList();
            if (!moves.isEmpty()) {
                for (Moves m : moves) {
                    em.remove(m);
                }
                em.getTransaction().commit();
                em.close();
            }
            return true;
        }
        return false;
    }
//find the biggest timestamp for this game, and delete row
    //before delete give client the string of move

    @Override
    public String undo() {
        String str = "";
        EntityManager em = SudokuServer.emf.createEntityManager();
        em.getTransaction().begin();
        Query q = em.createQuery("SELECT  m from Moves m where m.gameid=:gameid and m.player=:player ORDER BY m.time desc");
        q.setParameter("gameid", gameid);
        q.setParameter("player", myName);
        List<Moves> moves = (List<Moves>) q.getResultList();

        str = moves.get(0).getMove();
        em.remove(moves.get(0));
        em.getTransaction().commit();
        em.close();
        System.out.println("Move: " + str + "has been undoed.");
        return str;
    }
}
