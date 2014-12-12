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
    public BigDecimal calc() throws Exception {
        RMIUtil.setupPolicy();
        Calculator service = null;
        try {
            service = (Calculator) Naming.lookup(Static.CALCULATOR_SERVICE_NAME);
        } catch (NotBoundException e) {
            e.printStackTrace();
        } catch (MalformedURLException e) {
            e.printStackTrace();
        } catch (RemoteException e) {
            e.printStackTrace();
        }
        return service.pi(123);
    }
}
