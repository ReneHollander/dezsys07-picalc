package at.hollanderkalauner.picalc.client;

import at.hollanderkalauner.picalc.core.Calculator;
import at.hollanderkalauner.picalc.core.RMIUtil;
import at.hollanderkalauner.picalc.core.Static;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.math.BigDecimal;
import java.net.MalformedURLException;
import java.rmi.Naming;
import java.rmi.NotBoundException;
import java.rmi.RemoteException;

/**
 * Client
 *
 * @author Paul Kalauner
 * @version 20141212.1
 */
public class Client {
    private static final Logger LOG = LogManager.getLogger(Client.class);
    private Calculator service;
    private int decimalPlaces;

    /**
     * Initializes the Client
     *
     * @throws RemoteException
     * @throws NotBoundException
     * @throws MalformedURLException
     */
    public Client() throws RemoteException, NotBoundException, MalformedURLException {
        LOG.info("Initialising Client");
        RMIUtil.setupPolicy();
        service = (Calculator) Naming.lookup(Static.CALCULATOR_SERVICE_NAME);
    }

    /**
     * Sends a request to calculate Pi
     *
     * @return BigDecimal pi
     */
    public BigDecimal calc() {
        try {
            return service.pi(this.decimalPlaces);
        } catch (RemoteException e) {
            LOG.error("Error while calculating Pi: " + e.getMessage());
        }
        return null;
    }

    /**
     * Sets the decimalPlaces used to calculate Pi.
     *
     * @param decimalPlaces decimalPlaces used to calculate Pi
     */
    public void setDecimalPlaces(int decimalPlaces) {
        this.decimalPlaces = decimalPlaces;
    }
}
