package at.hollanderkalauner.picalc.client;

import org.apache.commons.cli.*;

/**
 * Parses the given arguments
 *
 * @author Paul Kalauner
 * @version 20141213.1
 */
public class ClientCLIParser {

    private int decimalPlaces;
    private String host;
    private int port;

    /**
     * Initializes the parser
     */
    public ClientCLIParser() {
        this.host = null;
        this.port = -1;
    }

    /**
     * Validates the given Arguments
     *
     * @param args arguments to validate
     * @return true if args are valid
     */
    @SuppressWarnings("AccessStaticViaInstance")
    public boolean checkArgs(String[] args) {
        Options options = new Options();
        options.addOption(OptionBuilder.withDescription("Shows a help dialog").create("help"));
        options.addOption(OptionBuilder.hasArg().withArgName("hostname").withLongOpt("host").withDescription("Hostname of the balancer").create('h'));
        options.addOption(OptionBuilder.hasArg().withArgName("portnumber").withLongOpt("port").withType(Number.class).withDescription("Port of the balancer").create('p'));
        options.addOption(OptionBuilder.hasArg().withArgName("decimalPlaces").isRequired().withType(Number.class).withDescription("The Number of decimal places used to calculate Pi.").withLongOpt("decimalplaces").create('d'));
        HelpFormatter hf = new HelpFormatter();
        CommandLineParser parser = new BasicParser();
        CommandLine cmd;

        try {
            cmd = parser.parse(options, args);

            if (cmd.hasOption("help")) {
                hf.printHelp("java -jar Client.jar", options, true);
                return false;
            }

            if (cmd.hasOption('h')) {
                this.host = cmd.getOptionValue('h');
            }

            if (cmd.hasOption('p')) {
                this.port = ((Number) cmd.getParsedOptionValue("p")).intValue();
            }


            this.decimalPlaces = ((Number) cmd.getParsedOptionValue("d")).intValue();

        } catch (ParseException e) {
            System.out.println(e.getMessage());
            hf.printHelp("java -jar Client.jar", options, true);
            return false;
        }
        return true;
    }

    /**
     * Gets the decimal places used to calculate Pi
     *
     * @return decimalPlaces
     */
    public int getDecimalPlaces() {
        return decimalPlaces;
    }

    /**
     * Gets the hostname of the balancer
     *
     * @return hostname
     */
    public String getHost() {
        return host;
    }

    /**
     * Gets the port of the balancer
     *
     * @return post
     */
    public int getPort() {
        return port;
    }
}
