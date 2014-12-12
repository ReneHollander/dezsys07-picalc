package at.hollanderkalauner.picalc.calculator;

import at.hollanderkalauner.picalc.core.Calculator;

import java.math.BigDecimal;
import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;

/**
 * Created by rene on 12/6/14.
 */
public class CalculatorService extends UnicastRemoteObject implements Calculator {

    public CalculatorService(int calculatorServerPort) throws RemoteException {
        super(calculatorServerPort);
    }

    @Override
    public BigDecimal pi(int decimalPlaces) {
        return BigDecimal.valueOf(Math.PI);
    }

}
