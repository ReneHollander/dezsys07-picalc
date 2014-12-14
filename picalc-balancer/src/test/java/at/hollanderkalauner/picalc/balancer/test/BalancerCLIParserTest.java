package at.hollanderkalauner.picalc.balancer.test;

import at.hollanderkalauner.picalc.balancer.Balancer;
import at.hollanderkalauner.picalc.balancer.BalancerCLIParser;
import at.hollanderkalauner.picalc.core.calculationbehaviour.GaussLegendre;
import at.hollanderkalauner.picalc.core.calculationbehaviour.RamanujanFormula;
import org.junit.Before;
import org.junit.Test;

import java.rmi.RemoteException;

import static org.junit.Assert.assertEquals;

/**
 * Tests for the argument parser for the Balancer
 *
 * @author Paul Kalauner
 * @version 20141214.1
 */
public class BalancerCLIParserTest {
    private Balancer balancer;
    private BalancerCLIParser parser;

    /**
     * Initializes {@code Balancer} and {@code BalancerCLIParser} before every test case
     *
     * @throws RemoteException should not happen
     */
    @Before
    public void setUp() throws RemoteException {
        this.balancer = new Balancer();
        this.parser = new BalancerCLIParser(balancer);
    }

    /**
     * Tests the parser with no given args. <br>
     * The default CalculationBehaviour (GaussLegendre) should be used.
     */
    @Test
    public void testNoArgs() {
        String[] args = new String[]{""};
        assertEquals(true, this.parser.checkArgs(args));
        assertEquals(true, this.parser.getCalcBehav() instanceof GaussLegendre);
    }

    /**
     * Tests the parser with a valid behaviour argument
     */
    @Test
    public void testBehaviourArg() {
        String[] args = new String[]{"-b", "ramanujanformula"};
        assertEquals(true, this.parser.checkArgs(args));
        assertEquals(true, this.parser.getCalcBehav() instanceof RamanujanFormula);
    }

    /**
     * Tests the parser with an invalid behaviour argument
     */
    @Test
    public void testInvalidBehaviourArg() {
        String[] args = new String[]{"-b", "invalidbehaviour"};
        assertEquals(false, this.parser.checkArgs(args));
    }

    /**
     * Tests the parser with an invalid behaviour argument
     */
    @Test
    public void testInvalidBehaviourArg2() {
        String[] args = new String[]{"-b"};
        assertEquals(false, this.parser.checkArgs(args));
    }

    /**
     * Tests the parser with the help option
     */
    @Test
    public void testHelpOption() {
        String[] args = new String[]{"--help"};
        assertEquals(false, this.parser.checkArgs(args));
    }
}
