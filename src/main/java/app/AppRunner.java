package app;

import cli.CarFetcherCli;

/**
 * Application entry point
 *
 * @author Alex Pumnea
 */

public class AppRunner {
    public static void main(String[] args) {
        CarFetcherCli app = new CarFetcherCli();
        app.run();
    }
}
