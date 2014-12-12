package at.hollanderkalauner.picalc.calculator;

import at.hollanderkalauner.picalc.core.Static;

import java.net.MalformedURLException;
import java.rmi.AlreadyBoundException;
import java.rmi.Naming;
import java.rmi.RemoteException;

/**
 * Created by rene on 12/6/14.
 */
public class Main {

    public static void main(String[] args) throws RemoteException, AlreadyBoundException, MalformedURLException {
        if (System.getSecurityManager() == null) {
            System.setSecurityManager(new SecurityManager());
        }

        CalculatorService calculatorServer = new CalculatorService(Static.CALCULATOR_SERVER_PORT);
        Naming.bind(Static.CALCULATOR_SERVER_NAME, calculatorServer);
        System.out.println("Service " + Static.CALCULATOR_SERVER_NAME + " bound!");
    }

}
