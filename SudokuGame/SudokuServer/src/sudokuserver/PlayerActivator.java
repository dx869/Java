/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package sudokuserver;

import corbaobjects.PlayerImpl;
import org.omg.CORBA.LocalObject;
import org.omg.PortableServer.ForwardRequest;
import org.omg.PortableServer.POA;
import org.omg.PortableServer.Servant;
import org.omg.PortableServer.ServantActivator;

/**
 *
 * @author rebecca
 */
public class PlayerActivator extends LocalObject implements ServantActivator {

    @Override
    public Servant incarnate(byte[] oid, POA adapter) throws ForwardRequest {
        String name = new String(oid);
        System.out.println("Activating Player " + name);
       PlayerImpl gpi = new PlayerImpl(name);
       
        return gpi;
    }

    @Override
    public void etherealize(byte[] oid,
            POA adapter,
            Servant serv,
            boolean cleanup_in_progress,
            boolean remaining_activations) {
        String name = new String(oid);
        System.out.println("Etherealizing Player " + name);
       PlayerImpl gpi = (PlayerImpl) serv;
       gpi.saveData();
        System.out.println(name + " should have saved data");
    }
}
