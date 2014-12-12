package at.hollanderkalauner.picalc.calculator;

import at.hollanderkalauner.picalc.core.CalculationBehaviour;
import at.hollanderkalauner.picalc.core.Calculator;
import at.hollanderkalauner.picalc.core.Static;

import java.math.BigDecimal;
import java.net.MalformedURLException;
import java.rmi.Naming;
import java.rmi.NotBoundException;
import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;

/**
 * Created by rene on 12/6/14.
 */
public class CalculatorService extends UnicastRemoteObject implements Calculator {

    public CalculatorService() throws RemoteException {
        super();
    }

    @Override
    public BigDecimal pi(int decimalPlaces) throws RemoteException {
        try {
            CalculationBehaviour calculationBehaviour = (CalculationBehaviour) Naming.lookup(Static.CALCULATOR_CALCULATIONBEHAVIOUR_NAME);
            return calculationBehaviour.calculatePi(decimalPlaces);
        } catch (NotBoundException e) {
            e.printStackTrace();
        } catch (MalformedURLException e) {
            e.printStackTrace();
        }
        return null;
    }

}
