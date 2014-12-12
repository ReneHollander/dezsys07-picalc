package at.hollanderkalauner.picalc.balancer;

import java.net.MalformedURLException;
import java.rmi.AlreadyBoundException;
import java.rmi.RemoteException;

/**
 * Created by rene on 12/6/14.
 */
public class Main {

    public static void main(String[] args) throws MalformedURLException, RemoteException, AlreadyBoundException {
        Balancer b = new Balancer();
        b.bind();
    }

}
