/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package sudokuserver;

import corbaobjects.AdminImpl;
import corbaobjects.PlayerImpl;
import java.sql.Connection;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;
import org.omg.CORBA.ORB;
import org.omg.CORBA.ORBPackage.InvalidName;
import org.omg.CORBA.Policy;
import org.omg.CosNaming.NameComponent;
import org.omg.CosNaming.NamingContext;
import org.omg.CosNaming.NamingContextHelper;
import org.omg.CosNaming.NamingContextPackage.AlreadyBound;
import org.omg.CosNaming.NamingContextPackage.CannotProceed;
import org.omg.CosNaming.NamingContextPackage.NotFound;
import org.omg.PortableServer.IdAssignmentPolicyValue;
import org.omg.PortableServer.LifespanPolicyValue;
import org.omg.PortableServer.POA;
import org.omg.PortableServer.POAHelper;
import org.omg.PortableServer.POAManager;
import org.omg.PortableServer.POAManagerPackage.AdapterInactive;
import org.omg.PortableServer.POAPackage.ObjectAlreadyActive;
import org.omg.PortableServer.POAPackage.ObjectNotActive;
import org.omg.PortableServer.POAPackage.ServantAlreadyActive;
import org.omg.PortableServer.POAPackage.WrongPolicy;
import org.omg.PortableServer.RequestProcessingPolicyValue;
import org.omg.PortableServer.ServantActivator;
import org.omg.PortableServer.ServantRetentionPolicyValue;

/**
 *
 * @author rebecca
 */
public class SudokuServer {

    private Connection conn = null;
    public static POA adminPOA;
    public static POA playerPOA;
    private static POA root_poa;
    private static POAManager poa_manager;
    public static ORB orb;
    public static NamingContext rootctx;
    public static EntityManagerFactory emf;
    
    
    public static void main(String[] args) {
        try {
            emf = Persistence.createEntityManagerFactory("SudokuPersistencePU");
            System.out.println("Initializing orb");
                    orb = ORB.init(args, null);
                    org.omg.CORBA.Object poaobj=null;
            try {
                poaobj = orb.resolve_initial_references("RootPOA");
            } catch (InvalidName ex) {
                Logger.getLogger(SudokuServer.class.getName()).log(Level.SEVERE, null, ex);
            }
                    root_poa = POAHelper.narrow(poaobj);
                    poa_manager =root_poa.the_POAManager();

                    
                    System.out.println("Publishing to name service");
                    org.omg.CORBA.Object objRef=null;
            try {
                objRef = orb.resolve_initial_references("NameService");
            } catch (InvalidName ex) {
                Logger.getLogger(SudokuServer.class.getName()).log(Level.SEVERE, null, ex);
            }
                    rootctx = NamingContextHelper.narrow(objRef);

                    makeAdminPOA();
                    makePlayerPOA();
                    
                    // Create a ServantActivator
                    ServantActivator playerActivator =
                            new PlayerActivator();
                    
            try {
                playerPOA.set_servant_manager(playerActivator);
            } catch (WrongPolicy ex) {
                Logger.getLogger(SudokuServer.class.getName()).log(Level.SEVERE, null, ex);
            }

                    // Create administrator objects; and publish Corba references
                    // via name service
                    String adminName = "Administrator";
                   // String playerTableName = "PlayerTable";

                    AdminImpl anAdmin = new AdminImpl();
                   // PlayerImpl aPlayer= new PlayerImpl();

                    byte[] oidAdmin = adminName.getBytes();

                   // byte[] oidPlayer = playerTableName.getBytes();
            try {
                adminPOA.activate_object_with_id(oidAdmin, anAdmin);
            } catch (ServantAlreadyActive ex) {
                Logger.getLogger(SudokuServer.class.getName()).log(Level.SEVERE, null, ex);
            } catch (ObjectAlreadyActive ex) {
                Logger.getLogger(SudokuServer.class.getName()).log(Level.SEVERE, null, ex);
            } catch (WrongPolicy ex) {
                Logger.getLogger(SudokuServer.class.getName()).log(Level.SEVERE, null, ex);
            }
         


                    org.omg.CORBA.Object refAdmin=null;
            try {
                refAdmin = adminPOA.id_to_reference(oidAdmin);
            } catch (ObjectNotActive ex) {
                Logger.getLogger(SudokuServer.class.getName()).log(Level.SEVERE, null, ex);
            } catch (WrongPolicy ex) {
                Logger.getLogger(SudokuServer.class.getName()).log(Level.SEVERE, null, ex);
            }
                    org.omg.CORBA.Object refPlayer=null;





            NameComponent nc0 = new NameComponent("examples", "");
            NameComponent nc1 = new NameComponent("game", "");
            NameComponent nc2 = new NameComponent("Administrator", "");
            NameComponent path[] = {nc0, nc1, nc2};
            try {
                registerObjWithNameService(rootctx, path, refAdmin, true);
            } catch (InvalidName ex) {
                Logger.getLogger(SudokuServer.class.getName()).log(Level.SEVERE, null, ex);
            } catch (AlreadyBound ex) {
                Logger.getLogger(SudokuServer.class.getName()).log(Level.SEVERE, null, ex);
            }



                    System.out.println("Starting server");
                    poa_manager.activate();
                    orb.run();
                    System.out.println("Returned from orb.run");
                    Thread.sleep(2000);

                    // Maybe sun has closed down poa as part of orb shutdown
                  //  System.out.println("destroying orb");
                    //root_poa.destroy(true,false);
                   // orb.destroy();
                   // System.out.println("that is all gentlemen");
        } catch (CannotProceed ex) {
            Logger.getLogger(SudokuServer.class.getName()).log(Level.SEVERE, null, ex);
        } catch (NotFound ex) {
            Logger.getLogger(SudokuServer.class.getName()).log(Level.SEVERE, null, ex);
        } catch (org.omg.CosNaming.NamingContextPackage.InvalidName ex) {
            Logger.getLogger(SudokuServer.class.getName()).log(Level.SEVERE, null, ex);
        } catch (InterruptedException ex) {
            Logger.getLogger(SudokuServer.class.getName()).log(Level.SEVERE, null, ex);
        } catch (AdapterInactive ex) {
            Logger.getLogger(SudokuServer.class.getName()).log(Level.SEVERE, null, ex);
        }
    
    }
    private static void makePlayerPOA(){
        
            Policy[] playerPolicies = new Policy[4];
        playerPolicies[0] =
                root_poa.create_id_assignment_policy(IdAssignmentPolicyValue.USER_ID);
        playerPolicies[1] =
                root_poa.create_lifespan_policy(LifespanPolicyValue.PERSISTENT);
        playerPolicies[2] =
                root_poa.create_request_processing_policy(RequestProcessingPolicyValue.USE_SERVANT_MANAGER);
        playerPolicies[3] =
                root_poa.create_servant_retention_policy(ServantRetentionPolicyValue.RETAIN);
        String playerPoasName = "PlayerPOA";
        try {
            playerPOA =
                    root_poa.create_POA(playerPoasName,
                    poa_manager,
                    playerPolicies);
            
            System.out.println("PLayer POA created");
        } catch (Exception e) {
            System.out.println(e.toString());
            System.exit(1);
        }
    
    }
    private static void makeAdminPOA(){
        Policy[] adminPolicies = new Policy[2];

        adminPolicies[0] =
                root_poa.create_id_assignment_policy(IdAssignmentPolicyValue.USER_ID);
        adminPolicies[1] =
                root_poa.create_lifespan_policy(LifespanPolicyValue.PERSISTENT);
        String adminPoasName = "AdminPOA";
        try {
            adminPOA =
                    root_poa.create_POA(adminPoasName,
                    poa_manager,
                    adminPolicies);
        } catch (Exception e) {
            System.out.println(e.toString());
            System.exit(1);
        }
        System.out.println("Admin POA created");
    }
    public static void registerObjWithNameService(NamingContext root,
            NameComponent[] serverName, org.omg.CORBA.Object obj, boolean bind) throws
            InvalidName, AlreadyBound, CannotProceed, NotFound, org.omg.CosNaming.NamingContextPackage.InvalidName {
        if (bind) {
            System.out.println("Binding name in nameservice");
        } else {
            System.out.println("Unbinding name from nameservice");
        }
        NamingContext currentContext = root;

        NameComponent[] singleElement = new NameComponent[1];

        for (int i = 0; i < serverName.length - 1; i++) {
            System.out.print(serverName[i].id + "/");
            singleElement[0] = serverName[i];
            try {
                currentContext = NamingContextHelper.narrow(
                        currentContext.resolve(singleElement));

            } catch (NotFound nf) {

                currentContext =
                        currentContext.bind_new_context(singleElement);
            }

        }

        singleElement[0] = serverName[serverName.length - 1];
        System.out.println(singleElement[0].id);
        if (bind) {
            currentContext.rebind(singleElement, obj);
        } else {
            currentContext.unbind(singleElement);
        }

    }
}
