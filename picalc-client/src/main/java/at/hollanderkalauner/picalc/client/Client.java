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
     * Calculates Pi
     *
     * @return BigDecimal pi
     * @throws RemoteException if something is wrong
     */
    public BigDecimal calc() throws RemoteException {
        return service.pi(40000);
    }
}
