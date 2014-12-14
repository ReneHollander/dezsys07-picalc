package at.hollanderkalauner.picalc.client.test;

import at.hollanderkalauner.picalc.client.ClientCLIParser;
import org.junit.Before;
import org.junit.Test;

import java.rmi.RemoteException;

import static org.junit.Assert.assertEquals;

/**
 * Tests for the argument parser for the Client
 *
 * @author Paul Kalauner
 * @version 20141214.1
 */
public class ClientCLIParserTest {
    private ClientCLIParser parser;

    /**
     * Initializes {@code ClientCLIParser} before every test case
     *
     * @throws RemoteException should not happen
     */
    @Before
    public void setUp() throws RemoteException {
        this.parser = new ClientCLIParser();
    }

    /**
     * Tests the parser with valid args
     */
    @Test
    public void testValidArgs() {
        String[] args = new String[]{"-d", "40000"};
        assertEquals(true, this.parser.checkArgs(args));
        assertEquals(40000, this.parser.getDecimalPlaces());
    }

    /**
     * Tests the parser with no args (should be invalid)
     */
    @Test
    public void testNoArgs() {
        String[] args = new String[]{};
        assertEquals(false, this.parser.checkArgs(args));
    }

    /**
     * Tests the parser with an invalid number of decimal places
     */
    @Test
    public void testInvalidDecimalPlaces() {
        String[] args = new String[]{"-d", "abc"};
        assertEquals(false, this.parser.checkArgs(args));
    }

    /**
     * Tests the parser with valid args
     */
    @Test
    public void testValidArgsHostPort() {
        String[] args = new String[]{"-d", "40000", "-h", "hostname", "-p", "1234"};
        assertEquals(true, this.parser.checkArgs(args));
        assertEquals(40000, this.parser.getDecimalPlaces());
        assertEquals("hostname", this.parser.getHost());
        assertEquals(1234, this.parser.getPort());
    }

    /**
     * Tests the parser with an invalid portnumber
     */
    @Test
    public void testInvalidPort() {
        String[] args = new String[]{"-d", "40000", "-h", "hostname", "-p", "abc"};
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