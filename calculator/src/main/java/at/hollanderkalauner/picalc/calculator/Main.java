package at.hollanderkalauner.picalc.calculator;

import at.hollanderkalauner.picalc.core.RMIUtil;
import at.hollanderkalauner.picalc.core.Static;

import java.net.MalformedURLException;
import java.rmi.AlreadyBoundException;
import java.rmi.Naming;
import java.rmi.RemoteException;

/**
 * Created by rene on 12/6/14.
 */
public class Main {

    public static void main(String[] args) throws Exception {
        RMIUtil.setupPolicy();
        CalculatorService calculatorServer = new CalculatorService();
        Naming.bind(Static.CALCULATOR_SERVICE_NAME, calculatorServer);
        System.out.println("Service " + Static.CALCULATOR_SERVICE_NAME + " bound!");
    }

}
