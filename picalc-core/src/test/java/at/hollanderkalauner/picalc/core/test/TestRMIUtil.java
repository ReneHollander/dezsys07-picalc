package at.hollanderkalauner.picalc.core.test;

import at.hollanderkalauner.picalc.core.RMIUtil;
import org.junit.Test;

import java.net.MalformedURLException;
import java.rmi.AlreadyBoundException;
import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;

import static org.junit.Assert.assertEquals;

/**
 * Testing RMIUtil
 *
 * @author Rene Hollander
 * @version 20141214.1
 */
public class TestRMIUtil extends UnicastRemoteObject {
    //TODO: Fix tests

    /**
     * Constructor needed to test RMI
     *
     * @throws RemoteException if error
     */
    public TestRMIUtil() throws RemoteException {
    }

    /**
     * Test setting up the policy file
     */
    @Test
    public void testSetupPolicy() {
        RMIUtil.setupPolicy();
        assertEquals(System.getProperty("java.security.policy"), System.class.getResource("/policy/java.policy").toString());
    }

    /**
     * Test setting up registry
     *
     * @throws RemoteException       if error
     * @throws AlreadyBoundException if error
     * @throws MalformedURLException if error
     */
//    @Test
//    public void testSetupRegistry() throws RemoteException, AlreadyBoundException, MalformedURLException {
//        RMIUtil.setupRegistry();
//        Naming.bind("test", new EmptyRemoteImpl());
//    }

    /**
     * Test CreateRMIUrl without Hostname and Port
     */
//    @Test
//    public void testCreateRMIUrlWithoutHostnameAndPort() {
//        assertEquals("rmi://localhost/", RMIUtil.createRMIUrl(null, -1));
//    }

    /**
     * Test CreateRMIUrl without Port
     */
//    @Test
//    public void testCreateRMIUrlWithoutPort() {
//        assertEquals("rmi://localhost/", RMIUtil.createRMIUrl("localhost", -1));
//    }

    /**
     * Test CreateRMIUrl
     */
//    @Test
//    public void testCreateRMIUrl() {
//        assertEquals("rmi://localhost:1099/", RMIUtil.createRMIUrl("localhost", 1099));
//    }
}
