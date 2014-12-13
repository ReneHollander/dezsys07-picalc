package at.hollanderkalauner.picalc.core;

import java.io.Serializable;
import java.rmi.Remote;
import java.rmi.RemoteException;

/**
 * Calculator Registry to register and unregister Calculators used to balance load
 *
 * @author Rene Hollander
 * @version 20141213.1
 */
public interface CalculatorRegistry extends Remote, Serializable {

    /**
     * Registers the specified Calculator for load balancing
     *
     * @param c Calculator to register
     * @throws RemoteException if there was an error on the side of the service
     */
    public void registerCalculator(Calculator c) throws RemoteException;

    /**
     * Unregisters the specified Calculator for load balancing
     *
     * @param c Calculator to unregister
     * @throws RemoteException if there was an error on the side of the service
     */
    public void unregisterCalculator(Calculator c) throws RemoteException;

}
