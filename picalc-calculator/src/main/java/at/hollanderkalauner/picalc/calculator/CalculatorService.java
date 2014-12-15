package at.hollanderkalauner.picalc.calculator;

import at.hollanderkalauner.picalc.core.interfaces.RMICloseable;
import at.hollanderkalauner.picalc.core.interfaces.RMIStartable;
import at.hollanderkalauner.picalc.core.remoteobjects.CalculationBehaviour;
import at.hollanderkalauner.picalc.core.remoteobjects.Calculator;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.math.BigDecimal;
import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;

/**
 * Calculator Service
 *
 * @author Rene Hollander
 * @version 20141212.1
 */
public abstract class CalculatorService extends UnicastRemoteObject implements RMIStartable.Service, RMICloseable, Calculator {

    private static final Logger LOG = LogManager.getLogger(CalculatorService.class);

    /**
     * Initializes the CalculatorService
     *
     * @throws RemoteException on failure
     */
    public CalculatorService() throws RemoteException {
    }

    @Override
    public BigDecimal pi(int decimalPlaces) throws RemoteException {
        LOG.info("Calculating Pi to " + decimalPlaces + " decimal places");
        return this.getCalculationBehaviour().calculatePi(decimalPlaces);
    }

    /**
     * Get the used calculation behaviour
     *
     * @return Calculation Behaviour
     */
    public abstract CalculationBehaviour getCalculationBehaviour();

}
