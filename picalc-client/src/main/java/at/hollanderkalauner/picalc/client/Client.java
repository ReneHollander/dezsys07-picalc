package at.hollanderkalauner.picalc.client;

import at.hollanderkalauner.picalc.core.remoteobjects.Calculator;
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

    /**
     * Initializes the Client
     *
     * @param host Hostname for the Calculator
     * @param port Port of the calculator
     * @throws NotBoundException     if name is not currently bound
     * @throws RemoteException       if registry could not be contacted
     * @throws MalformedURLException if the name is not an appropriately
     */
    public Client(String host, int port) throws RemoteException, NotBoundException, MalformedURLException {
        LOG.info("Initialising Client");
        String rmiurl = RMIUtil.createRMIUrl(host, port);
        RMIUtil.setupPolicy();
        service = (Calculator) Naming.lookup(rmiurl + Static.CALCULATOR_SERVICE_NAME);
    }

    /**
     * Sends a request to calculate Pi
     *
     * @param decimalPlaces the decimalPlaces used to calculate Pi
     * @return BigDecimal pi
     */
    public BigDecimal calc(int decimalPlaces) {
        try {
            return service.pi(decimalPlaces);
        } catch (RemoteException e) {
            LOG.error("Error while calculating Pi: " + e.getMessage());
        }
        return null;
    }
}
