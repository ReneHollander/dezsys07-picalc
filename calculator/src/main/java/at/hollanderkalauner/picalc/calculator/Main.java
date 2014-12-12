package at.hollanderkalauner.picalc.calculator;

import at.hollanderkalauner.picalc.core.RMIUtil;
import at.hollanderkalauner.picalc.core.Static;

import java.rmi.Naming;

/**
 * Created by rene on 12/6/14.
 */
public class Main {

    public static void main(String[] args) throws Exception {
        RMIUtil.setupPolicy();
        RMIUtil.setupRegistry();
        CalculatorService calculatorService = new CalculatorService();
        Naming.bind(Static.CALCULATOR_SERVICE_NAME, calculatorService);
        System.out.println("Service " + Static.CALCULATOR_SERVICE_NAME + " bound!");
    }

}
