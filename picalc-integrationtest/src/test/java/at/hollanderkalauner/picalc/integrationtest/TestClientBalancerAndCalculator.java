package at.hollanderkalauner.picalc.integrationtest;

import at.hollanderkalauner.picalc.balancer.Balancer;
import at.hollanderkalauner.picalc.calculator.BehindBalancerCalculatorService;
import at.hollanderkalauner.picalc.client.Client;
import at.hollanderkalauner.picalc.core.calculationbehaviour.GaussLegendre;
import at.hollanderkalauner.picalc.core.testhelper.StaticTestValues;
import org.junit.Test;

import java.rmi.RemoteException;

import static org.junit.Assert.assertEquals;

/**
 * Integration Test Client, Balancer and Calculator
 *
 * @author Rene Hollander
 * @version 20141215.1
 */
public class TestClientBalancerAndCalculator {

    /**
     * Tests the program with a running Client, Balancer and a CalculatorService
     *
     * @throws Exception if error
     */
    @Test
    public void testClientBalancerAndCalculator() throws Exception {
        Balancer b = new Balancer(new GaussLegendre());
        b.start(2000);

        BehindBalancerCalculatorService bbcs = new BehindBalancerCalculatorService("localhost", 2000);
        bbcs.start();

        Client c = new Client();
        c.start("localhost", 2000);
        assertEquals(StaticTestValues.PI, c.calc(StaticTestValues.PI_DECIMAL_PLACES).doubleValue(), StaticTestValues.PI_DELTA);

        bbcs.close();
        b.close();
    }

    /**
     * Tests the Client and Balancer with no running CalculatorServices<br>
     * should throw a RemoteException
     *
     * @throws Exception if error
     */
    @Test(expected = RemoteException.class)
    public void testClientBalancerWithoutCalculator() throws Exception {
        Balancer b = new Balancer(new GaussLegendre());
        b.start(2001);

        Client c = new Client();
        c.start("localhost", 2001);
        assertEquals(StaticTestValues.PI, c.calc(StaticTestValues.PI_DECIMAL_PLACES).doubleValue(), StaticTestValues.PI_DELTA);

        b.close();
    }

}
