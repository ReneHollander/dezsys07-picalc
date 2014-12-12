package at.hollanderkalauner.picalc.core;

import java.io.Serializable;
import java.math.BigDecimal;
import java.rmi.Remote;
import java.rmi.RemoteException;

/**
 * Created by rene on 12/12/14.
 */
public interface Calculator extends Remote, Serializable {

    public BigDecimal pi(int decimalPlaces) throws RemoteException;

}