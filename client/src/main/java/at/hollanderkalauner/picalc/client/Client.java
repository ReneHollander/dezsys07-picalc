package at.hollanderkalauner.picalc.client;

import at.hollanderkalauner.picalc.core.Calculator;
import at.hollanderkalauner.picalc.core.RMIUtil;
import at.hollanderkalauner.picalc.core.Static;

import java.math.BigDecimal;
import java.net.MalformedURLException;
import java.rmi.Naming;
import java.rmi.NotBoundException;
import java.rmi.RemoteException;

/**
 * Client
 */
public class Client {
    private Calculator service;

    public Client() throws RemoteException, NotBoundException, MalformedURLException {
        RMIUtil.setupPolicy();
        service = (Calculator) Naming.lookup(Static.CALCULATOR_SERVICE_NAME);
    }

    public BigDecimal calc() throws Exception {
        return service.pi(100000);
    }
}
