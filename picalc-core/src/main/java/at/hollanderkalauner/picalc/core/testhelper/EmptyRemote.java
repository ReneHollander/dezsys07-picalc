package at.hollanderkalauner.picalc.core.testhelper;

import java.io.Serializable;
import java.rmi.Remote;
import java.rmi.RemoteException;

/**
 * A RemoteObject just for testing
 *
 * @author Rene Hollander
 * @version 20141214.1
 */
public interface EmptyRemote extends Remote, Serializable {

    /**
     * Just a method that does nothing
     *
     * @throws RemoteException if error
     */
    public void empty() throws RemoteException;
}
