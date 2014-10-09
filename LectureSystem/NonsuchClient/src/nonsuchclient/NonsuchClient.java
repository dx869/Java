/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package nonsuchclient;

import NonsuchInterface.NonsuchRemote;
import guistuff.NonsuchGUI;
import java.net.MalformedURLException;
import java.rmi.Naming;
import java.rmi.NotBoundException;
import java.rmi.RemoteException;
import java.util.logging.Level;
import java.util.logging.Logger;
import nonserver.Nonserver;

/**
 *
 * @author dx869
 */
public class NonsuchClient {
    
    //set up 
    private static final String hostName="localhost";
    private static final String SoccerServiceport="123";
    private static final String principal="Nonserver";
    private static NonsuchRemote myServer;

    private static void launchGUI(){
        java.awt.EventQueue.invokeLater(new Runnable(){
            @Override
            public void run(){
                //show the GUI
                new NonsuchGUI().setVisible(true);
            }
        });
    }
    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        
        
        //connect to remot
        String serviceName="rmi://"+hostName+":"+SoccerServiceport+"/"+principal;
        System.out.println("Contacting service "+serviceName);
        
        try{
           myServer=(NonsuchRemote) Naming.lookup(serviceName);
        }catch(  MalformedURLException  mfurle){
            System.out.println(mfurle);
            System.exit(1);
        } catch (NotBoundException ex) {
            Logger.getLogger(NonsuchClient.class.getName()).log(Level.SEVERE, null, ex);
        } catch (RemoteException ex) {
            Logger.getLogger(NonsuchClient.class.getName()).log(Level.SEVERE, null, ex);
        }
        
        
        launchGUI();
    }
    // oops  return a brand new RMINonsuchServer
     public static NonsuchRemote getServer(){
         return myServer;
     }

}
    

