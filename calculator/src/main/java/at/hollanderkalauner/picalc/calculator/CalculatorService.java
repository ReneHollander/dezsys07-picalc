package at.hollanderkalauner.picalc.calculator;

import at.hollanderkalauner.picalc.core.CalculationBehaviour;
import at.hollanderkalauner.picalc.core.Calculator;
import at.hollanderkalauner.picalc.core.Static;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.math.BigDecimal;
import java.net.MalformedURLException;
import java.rmi.Naming;
import java.rmi.NotBoundException;
import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;

/**
 * Calculator Service
 *
 * @author Rene Hollander
 * @version 20141212.1
 */
public class CalculatorService extends UnicastRemoteObject implements Calculator {
    private static final Logger LOG = LogManager.getLogger(CalculatorService.class);

    /**
     * Initializes the CalculatorService
     *
     * @throws RemoteException on failure
     */
    public CalculatorService() throws RemoteException {
        super();
        LOG.info("Initializing CalculatorService");
    }

    @Override
    public BigDecimal pi(int decimalPlaces) throws RemoteException {
        try {
            LOG.info("Calculating Pi");
            CalculationBehaviour calculationBehaviour = (CalculationBehaviour) Naming.lookup(Static.CALCULATOR_CALCULATIONBEHAVIOUR_NAME);
            return calculationBehaviour.calculatePi(decimalPlaces);
        } catch (NotBoundException e) {
            LOG.error(Static.CALCULATOR_CALCULATIONBEHAVIOUR_NAME + " is not bound!");
        } catch (MalformedURLException e) {
            LOG.error("Malformed URL: " + e.getMessage());
        }
        return null;
    }
}
