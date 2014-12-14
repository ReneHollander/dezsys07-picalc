package at.hollanderkalauner.picalc.core.testhelper;

import java.rmi.RemoteException;

public class EmptyRemoteImpl implements EmptyRemote {
    @Override
    public void empty() throws RemoteException {
        // Just nothing
    }
}
