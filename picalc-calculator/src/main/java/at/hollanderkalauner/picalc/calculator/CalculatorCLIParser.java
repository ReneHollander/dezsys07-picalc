package at.hollanderkalauner.picalc.calculator;

import at.hollanderkalauner.picalc.core.CalculationBehaviour;
import at.hollanderkalauner.picalc.core.calculationbehaviour.CalculationBehaviourFactory;
import at.hollanderkalauner.picalc.core.calculationbehaviour.GaussLegendre;
import at.hollanderkalauner.picalc.core.calculationbehaviour.IllegalBehaviourException;
import org.apache.commons.cli.*;

/**
 * Parses the given arguments
 *
 * @author Paul Kalauner
 * @version 20141213.1
 */
public class CalculatorCLIParser {

    private static final int MODE_STANDALONE = -1;
    private static final int MODE_BEHINDBALANCER = 1;


    private CalculatorService calculator;
    private CalculationBehaviour calcBehav;

    private int mode = 0;
    private String host;
    private int port;

    /**
     * Initializes the parser with the given {@code CalculatorService} instance
     *
     * @param calculator instance of {@code CalculatorService}
     */
    public CalculatorCLIParser(CalculatorService calculator) {
        this.calculator = calculator;
        this.host = null;
        this.port = -1;
    }

    /**
     * Checks arguments and binds the calculator if the arguments are valid
     *
     * @param args arguments that will be validated
     */
    public void start(String[] args) {
        if (checkArgs(args))
            bind();
        else System.exit(0);
    }

    /**
     * Validates the given Arguments
     *
     * @param args arguments to validate
     * @return true if args are valid
     */
    @SuppressWarnings("AccessStaticViaInstance")
    private boolean checkArgs(String[] args) {
        Options options = new Options();
        options.addOption(OptionBuilder.withDescription("Shows a help dialog").create("help"));
        options.addOption(OptionBuilder.hasArg().withArgName("hostname").withLongOpt("host").withDescription("Hostname of the balancer (Default: localhost)").create('h'));
        options.addOption(OptionBuilder.hasArg().withArgName("portnumber").withLongOpt("port").withType(Number.class).withDescription("Port of the balancer (Default: 1099)").create('p'));
        options.addOption(OptionBuilder.withDescription("Starts the CalculatorService without Balancer").create("standalone"));
        options.addOption(OptionBuilder.hasArg().withArgName("behaviour").withDescription("The algorithm used to calculate Pi. Valid options: gausslegendre, ramanujanformula " +
                "(This argument is only used when using CalculatorService without Balancer) ").withLongOpt("behaviour").create('b'));
        HelpFormatter hf = new HelpFormatter();
        CommandLineParser parser = new BasicParser();
        CommandLine cmd = null;

        try {
            cmd = parser.parse(options, args);

            if (cmd.hasOption("help")) {
                hf.printHelp("java -jar Balancer.jar", options, true);
                return false;
            }

            if (cmd.hasOption("standalone")) {
                this.mode = MODE_STANDALONE;
                if (cmd.hasOption('b')) {
                    calcBehav = CalculationBehaviourFactory.createBehaviour(cmd.getOptionValue('b'));
                } else {
                    calcBehav = new GaussLegendre();
                }
            } else {
                this.mode = MODE_BEHINDBALANCER;
                if (cmd.hasOption('h')) {
                    this.host = cmd.getOptionValue('h');
                }

                if (cmd.hasOption('p')) {
                    this.port = ((Number) cmd.getParsedOptionValue("p")).intValue();
                }
            }
        } catch (IllegalBehaviourException ibe) {
            System.out.println("Invalid Behaviour " + (cmd != null ? cmd.getOptionValue('b') : null));
            return false;
        } catch (ParseException e) {
            System.out.println(e.getMessage());
            hf.printHelp("java -jar Balancer.jar", options, true);
            return false;
        }
        return true;
    }

    /**
     * Binds the CalculatorService
     */
    private void bind() {
        if (this.mode == MODE_STANDALONE) {
            this.calculator.bindStandalone(calcBehav);
        } else if (this.mode == MODE_BEHINDBALANCER) {
            this.calculator.bindToBalancer(this.host, this.port);
        }
    }
}
