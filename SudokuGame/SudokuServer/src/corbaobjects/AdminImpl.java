/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package corbaobjects;

import Aphorism.AdminPOA;
import Aphorism.PlayerHelper;
import SudokuPersistence.Players;
import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;
import javax.persistence.Query;
import org.omg.CosNaming.NameComponent;
import org.omg.PortableServer.POA;
import sudokuserver.SudokuServer;

/**
 *
 * @author rebecca
 */
public class AdminImpl extends AdminPOA {

    private EntityManagerFactory emf;
    private EntityManager em;

    public AdminImpl() {
        emf = Persistence.createEntityManagerFactory("SudokuPersistencePU");
        em = emf.createEntityManager();
    }

    @Override
    public void shutdownServer() {

        SudokuServer.orb.shutdown(false);
    }

    private boolean registerPlayerWithNameService(String name) {
        try {
            // Create a Corba object (but not a Player servant object)
            // and register in name service
            POA mypoa = SudokuServer.playerPOA;

            byte[] oidPlayer = name.getBytes();


            org.omg.CORBA.Object refPlayer =
                    mypoa.create_reference_with_id(oidPlayer, PlayerHelper.id());
            NameComponent nc0 = new NameComponent("Sudo", "");
            NameComponent nc1 = new NameComponent("players", "");
            NameComponent nc2 = new NameComponent(name, "");
            NameComponent path[] = {nc0, nc1, nc2};

            SudokuServer.registerObjWithNameService(SudokuServer.rootctx, path, refPlayer, true);
            return true;
        } catch (Exception e) {
            System.out.println("Admin got exception while registering a name ");
            System.out.println(e);
            return false;
        }
    }

    private void removePlayerFromNameService(String name) {
        NameComponent nc0 = new NameComponent("Sudo", "");
        NameComponent nc1 = new NameComponent("players", "");
        NameComponent nc2 = new NameComponent(name, "");
        NameComponent path[] = {nc0, nc1, nc2};
        try {
            SudokuServer.registerObjWithNameService(SudokuServer.rootctx, path, null, false);
        } catch (Exception e) {
        }
    }

    @Override
    public boolean createPlayer(String name, String password) {
        try {
            em.getTransaction().begin();
            Players p = new Players();
            p.setName(name);
            p.setPassword(password);
            p.setCurrentmove("0");
            em.persist(p);
            em.getTransaction().commit();  
            registerPlayerWithNameService(name);
            
            return true;
        } catch (Exception e) {

            return false;
        }

    }

    @Override
    public String [] getnames() {
       Query q = em.createNamedQuery("Players.findAll");
        List<Players> player = (List<Players>) q.getResultList();
        
        String [] namelist=new String[player.size()];
        int i = 0;
       for (Players p : player) {
           namelist[i]= p.getName();
            i++;
        }
        
        return namelist;
    }

    @Override
    public boolean changePassword(String name, String password) {

        try {
            em.getTransaction().begin();
            Query q = em.createQuery("UPDATE players SET players.password=:password WHERE players.name=:name");
            q.setParameter("name", name);
            q.setParameter("password", password);
            em.getTransaction().commit();
            em.close();
            return true;
        } catch (Exception e) {
            System.out.println("Error in AdminImpl: " + e.toString());
            return false;
        }

    }

    @Override
    public boolean deletePlayer(String name) {
        try {

            byte[] oidPlayer = name.getBytes();
            SudokuServer.playerPOA.deactivate_object(oidPlayer);

           // removePlayerFromNameService(name);
            em.getTransaction().begin();
            Query q = em.createNamedQuery("Players.findByName");
            q.setParameter("name", name);
            Players p = (Players) q.getSingleResult();
            em.remove(p);
            em.getTransaction().commit();
            em.close();
            return true;
        } catch (Exception e) {
           
            System.out.println("Error in AdminImpl: " + e.toString());
            return false;
        }

    }

}
