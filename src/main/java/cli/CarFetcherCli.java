package cli;

/**
 * Command Line Interface
 *
 * @author Alex Pumnea
 */
public class CarFetcherCli {
    private final Cli cli;

    public CarFetcherCli() {
        cli = new Cli();
    }

    public void run() {
        cli.displayMenu();
        cli.initApp();
        cli.displayResults();
    }
}
