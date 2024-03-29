package at.hollanderkalauner.picalc.calculator.test;

import at.hollanderkalauner.picalc.calculator.CalculatorCLIParser;
import at.hollanderkalauner.picalc.core.calculationbehaviour.GaussLegendre;
import at.hollanderkalauner.picalc.core.calculationbehaviour.RamanujanFormula;
import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.assertEquals;

/**
 * Tests for the argument parser for the CalculatorService
 *
 * @author Paul Kalauner
 * @version 20141214.1
 */
public class CalculatorCLIParserTest {
    private CalculatorCLIParser parser;

    /**
     * Initializes {@code CalculatorCLIParser} before every test case
     */
    @Before
    public void setUp() {
        this.parser = new CalculatorCLIParser();
    }

    /**
     * Tests the parser with no given args. <br>
     * The default host and port should be used.
     */
    @Test
    public void testNoArgs() {
        String[] args = new String[]{};
        assertEquals(true, this.parser.checkArgs(args));
    }

    /**
     * Tests the parser with valid args.
     */
    @Test
    public void testValidArgs() {
        String[] args = new String[]{"-h", "hostname", "-p", "1234"};
        assertEquals(true, this.parser.checkArgs(args));
        assertEquals("hostname", this.parser.getHost());
        assertEquals(1234, this.parser.getPort());
    }

    /**
     * Tests the parser with an invalid port number.
     */
    @Test
    public void testInvalidPort() {
        String[] args = new String[]{"-h", "hostname", "-p", "abc"};
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

    /**
     * Tests the parser with the standalone option and no behaviour given
     */
    @Test
    public void testStandAloneOption() {
        String[] args = new String[]{"--standalone"};
        assertEquals(true, this.parser.checkArgs(args));
        assertEquals(true, this.parser.getCalcBehav() instanceof GaussLegendre);
    }

    /**
     * Tests the parser with the standalone option and a given behaviour
     */
    @Test
    public void testStandAloneWithBehaviour() {
        String[] args = new String[]{"--standalone", "-b", "ramanujanformula"};
        assertEquals(true, this.parser.checkArgs(args));
        assertEquals(true, this.parser.getCalcBehav() instanceof RamanujanFormula);
    }

    /**
     * Tests the parser with the standalone option and an invalid behaviour
     */
    @Test
    public void testStandAloneInvalidBehaviour() {
        String[] args = new String[]{"--standalone", "-b", "invalidbehaviour"};
        assertEquals(false, this.parser.checkArgs(args));
    }
}