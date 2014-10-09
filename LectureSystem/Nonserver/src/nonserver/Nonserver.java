/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package nonserver;

import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;

/**
 *
 * @author Administrator
 */
public class Nonserver {

        private static final int myPort=123;
    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        RMINonsuchServer g=null;
        try{
            g=new RMINonsuchServer();
        }catch (Exception re){
            System.out.println("unable to start up server");
            System.out.println(re);
            System.exit(1);
        }
        String bindName="Nonserver";
        Registry rmiNamingService=null;
        try{
            // rmi registry , so client can send stub files.
            rmiNamingService=LocateRegistry.createRegistry(myPort);
        }catch(RemoteException re){
                System.out.println(re);
                System.exit(1);
        }
        System.out.println("About to bind  :"+ bindName);
        try{
                rmiNamingService.rebind(bindName,g);
            }catch(Exception e){
                System.out.println(e);
                System.exit(1);
            }
        System.out.println("yes it is working");
            
        }
}
