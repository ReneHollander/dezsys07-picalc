package at.hollanderkalauner.picalc.core.testhelper;

import java.io.Serializable;
import java.rmi.Remote;
import java.rmi.RemoteException;

/**
 * Created by rene on 12/14/14.
 */
public interface EmptyRemote extends Remote, Serializable {
    public void empty() throws RemoteException;
}
