package at.hollanderkalauner.picalc.integrationtest;

import at.hollanderkalauner.picalc.calculator.CalculatorService;
import at.hollanderkalauner.picalc.client.Client;
import at.hollanderkalauner.picalc.core.calculationbehaviour.GaussLegendre;
import at.hollanderkalauner.picalc.core.testhelper.StaticTestValues;
import org.junit.Test;

import java.net.MalformedURLException;
import java.rmi.NotBoundException;
import java.rmi.RemoteException;

import static org.junit.Assert.assertEquals;

/**
 * Integration Test Client and Calculator
 *
 * @author Rene Hollander
 * @version 20141214.1
 */
public class TestClientAndCalculator {

    private static final double PI = 3.14159265359;
    private static final int PI_DECIMAL_PLACES = 11;

    /**
     * Test the client without any calculators running
     *
     * @throws RemoteException       if error
     * @throws NotBoundException     if error
     * @throws MalformedURLException if error
     */
    @Test(expected = NotBoundException.class)
    public void testClientWithoutCalculatorRunning() throws RemoteException, NotBoundException, MalformedURLException {
        Client c = new Client();
        c.calc(100);
    }

    /**
     * Test the client without any calculators running
     *
     * @throws RemoteException       if error
     * @throws NotBoundException     if error
     * @throws MalformedURLException if error
     */
    @Test
    public void testClientWithCalculator() throws RemoteException, NotBoundException, MalformedURLException {
        CalculatorService calculatorService = new CalculatorService();
        calculatorService.bindStandalone(new GaussLegendre());

        Client c = new Client();
        assertEquals(StaticTestValues.PI, c.calc(StaticTestValues.PI_DECIMAL_PLACES).doubleValue(), StaticTestValues.PI_DELTA);
    }

}
