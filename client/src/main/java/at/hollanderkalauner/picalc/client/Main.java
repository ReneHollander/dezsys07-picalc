package at.hollanderkalauner.picalc.client;

/**
 * Created by rene on 12/6/14.
 */
public class Main {

    public static void main(String[] args) throws Exception {
        System.out.println(1);
        Client c = new Client();
        System.out.println(c.calc().toString());
        System.out.println(2);
    }

}
