package at.hollanderkalauner.picalc.calculator;

import at.hollanderkalauner.picalc.core.calculationbehaviour.GaussLegendre;
import at.hollanderkalauner.picalc.core.Calculator;
import at.hollanderkalauner.picalc.core.CalculatorRegistry;
import at.hollanderkalauner.picalc.core.RMIUtil;
import at.hollanderkalauner.picalc.core.Static;

import java.net.MalformedURLException;
import java.rmi.AlreadyBoundException;
import java.rmi.Naming;
import java.rmi.NotBoundException;
import java.rmi.RemoteException;

/**
 * Created by rene on 12/6/14.
 */
public class Main {

    public static void main(String[] args) throws Exception {
        RMIUtil.setupPolicy();

        startBehindBalancer();
    }

    private static void startBehindBalancer() throws RemoteException, NotBoundException, MalformedURLException {
        Calculator calculator = new CalculatorService();

        CalculatorRegistry calculatorRegistry = (CalculatorRegistry) Naming.lookup(Static.BALANCER_CALCULATORREGISTRY_NAME);
        calculatorRegistry.registerCalculator(calculator);
    }

    private static void startIndependent() throws RemoteException, AlreadyBoundException, MalformedURLException {
        RMIUtil.setupRegistry();

        //Naming.bind(Static.CALCULATOR_CALCULATIONBEHAVIOUR_NAME, new RamanujanFormula());
        Naming.bind(Static.CALCULATOR_CALCULATIONBEHAVIOUR_NAME, new GaussLegendre());

        Naming.bind(Static.CALCULATOR_SERVICE_NAME, new CalculatorService());

        System.out.println("Service " + Static.CALCULATOR_SERVICE_NAME + " bound!");
    }

}
