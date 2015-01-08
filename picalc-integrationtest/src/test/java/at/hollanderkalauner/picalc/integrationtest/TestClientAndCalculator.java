package at.hollanderkalauner.picalc.integrationtest;

import at.hollanderkalauner.picalc.calculator.StandaloneCalculatorService;
import at.hollanderkalauner.picalc.client.Client;
import at.hollanderkalauner.picalc.core.RMIUtil;
import at.hollanderkalauner.picalc.core.calculationbehaviour.GaussLegendre;
import at.hollanderkalauner.picalc.core.testhelper.StaticTestValues;
import org.junit.Test;

import static org.junit.Assert.assertEquals;

/**
 * Integration Test Client and Calculator
 *
 * @author Rene Hollander
 * @version 20141215.1
 */
public class TestClientAndCalculator {

    static {
        RMIUtil.setHostname();
    }

    /**
     * Test client with a calculator
     *
     * @throws Exception if error
     */
    @Test
    public void testClientWithCalculator() throws Exception {
        StandaloneCalculatorService calculatorService = new StandaloneCalculatorService(new GaussLegendre());
        calculatorService.start();

        Client c = new Client();
        c.start();
        assertEquals(StaticTestValues.PI, c.calc(StaticTestValues.PI_DECIMAL_PLACES).doubleValue(), StaticTestValues.PI_DELTA);

        calculatorService.close();
    }

}
