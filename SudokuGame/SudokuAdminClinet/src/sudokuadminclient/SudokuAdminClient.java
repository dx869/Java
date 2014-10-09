/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package sudokuadminclient;

import Aphorism.Admin;
import Aphorism.AdminHelper;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.logging.Level;
import java.util.logging.Logger;
import org.omg.CORBA.ORB;
import org.omg.CosNaming.NameComponent;
import org.omg.CosNaming.NamingContext;
import org.omg.CosNaming.NamingContextHelper;

/**
 *
 * @author rebecca
 */
public class SudokuAdminClient {

    private static Admin myAdmin;
    private static BufferedReader input;

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        try {

            ORB orb = ORB.init(args, null);

            org.omg.CORBA.Object objRef =
                    orb.resolve_initial_references("NameService");
            NamingContext initctx = NamingContextHelper.narrow(objRef);

            NameComponent nc0 = new NameComponent("examples", "");
            NameComponent nc1 = new NameComponent("game", "");
            NameComponent nc2 = new NameComponent("Administrator", "");
            NameComponent path[] = {nc0, nc1, nc2};

            myAdmin = AdminHelper.narrow(initctx.resolve(path));
            System.out.println("Have refrence to  service");


            start();

        } catch (Exception e) {
            System.out.println("Failed because : " + e);
            e.printStackTrace();
        }
    }

    private static void start() {
        try {
            String str = "l";
            while (!str.equals("q")) {
                System.out.println("Your can list players, add players, delete palyer adn quit player");
                System.out.println("list/add/delete/quit :");
                input = new BufferedReader(
                        new InputStreamReader(System.in));
                str = input.readLine();
                switch (str) {
                    case "list":
                        list();
                        break;
                    case "add":
                        add();
                        break;
                    case "delete":
                        delete();
                        break;
                    case "quit":
                        quit();
                        break;
                }

            }
        } catch (IOException ex) {
            Logger.getLogger(SudokuAdminClient.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    public static  void list() {
        try{
        String  [] name = myAdmin.getnames();
        for (int i = 0; i < name.length; i++) {
            System.out.print(name[i] + " ");
        }
        System.out.println("");
        }catch (Exception e){System.err.println(e);}
    }

    public static void add() throws IOException {
        String player;
        String password;
        System.out.println("Enter the player name and password");
        System.out.print("name : ");
        player = input.readLine().trim();
        System.out.print("password : " );
        password = input.readLine().trim();

        boolean result = myAdmin.createPlayer(player, password);
        if (result) {
            System.out.println("Player: " + player + "account created");
        } else {
            System.out.println("Failed to creat account");
        }

    }

    public static void delete() {
        	try {
   			 System.out.println("Enter the name of the player to be removed");
	    		System.out.print("Player name : ");
	   		String player = input.readLine().trim();

	   		 boolean result = myAdmin.deletePlayer(player);
	    		if(result)
				System.out.println("Account deleted");
	   		else
				System.out.println("Unable to delete account" +
					" (probably incorrect player name)");

		}
		catch(Exception e) {
			System.out.println("Failure : " + e.toString());
			System.exit(1);
		}
    }

    public static  void quit() {
        
        myAdmin.shutdownServer();
    }
}
