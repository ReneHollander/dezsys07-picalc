package at.hollanderkalauner.picalc.core.remoteobjects;

import java.io.Serializable;
import java.math.BigDecimal;
import java.rmi.Remote;
import java.rmi.RemoteException;

/**
 * Calculator Interface
 *
 * @author Rene Hollander
 * @version 20141212.1
 */
public interface Calculator extends Remote, Serializable {

    /**
     * Calculates Pi with the given decimalPlaces
     *
     * @param decimalPlaces decimalPlaces
     * @return Pi as BigDecimal
     * @throws RemoteException on failure
     */
    public BigDecimal pi(int decimalPlaces) throws RemoteException;

}
