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
        Calculator calculator = new CalculatorService(CalculatorService.Mode.BEHINDBALANCER);
    }



}
