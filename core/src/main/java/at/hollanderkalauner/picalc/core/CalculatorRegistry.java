package at.hollanderkalauner.picalc.core;

import java.io.Serializable;
import java.rmi.Remote;
import java.rmi.RemoteException;

/**
 * Created by rene on 12/12/14.
 */
public interface CalculatorRegistry extends Remote, Serializable {

    public void registerCalculator(Calculator c) throws RemoteException;

}
