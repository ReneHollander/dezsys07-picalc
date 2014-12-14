package at.hollanderkalauner.picalc.core.testhelper;

import java.rmi.RemoteException;

/**
 * Just the Implementation of an Empty RemoteObject for testing
 *
 * @author Rene Hollander
 * @version 20141214.1
 */
public class EmptyRemoteImpl implements EmptyRemote {
    @Override
    public void empty() throws RemoteException {
        // Just nothing
    }
}
