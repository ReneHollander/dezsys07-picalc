package at.hollanderkalauner.picalc.client;

import org.apache.commons.cli.*;

/**
 * Created by Paul on 13.12.14.
 */
public class ClientCLIParser {
    private Client client;

    /**
     * Initializes the parser with the given {@code Client} instance
     *
     * @param client instance of {@code Client}
     */
    public ClientCLIParser(Client client) {
        this.client = client;
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
        options.addOption(OptionBuilder.withDescription("Shows a help dialog").withLongOpt("help").create('h'));
        options.addOption(OptionBuilder.hasArg().withArgName("decimalPlaces").isRequired().withType(Number.class).withDescription("The Number of decimal places used to calculate Pi.").withLongOpt("decimalplaces").create('d'));
        HelpFormatter hf = new HelpFormatter();
        CommandLineParser parser = new BasicParser();
        CommandLine cmd;

        try {
            cmd = parser.parse(options, args);

            if (cmd.hasOption('h')) {
                hf.printHelp("java -jar Client.jar", options, true);
                return false;
            }

            client.setDecimalPlaces(((Number) cmd.getParsedOptionValue("d")).intValue());

        } catch (ParseException e) {
            System.out.println(e.getMessage());
            hf.printHelp("java -jar Client.jar", options, true);
            return false;
        }
        return true;
    }

}
