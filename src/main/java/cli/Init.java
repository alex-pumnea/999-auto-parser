package cli;

import controller.CarController;
import model.Car;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import repository.CarRepository;
import repository.Repository;
import service.CarService;

import java.util.Scanner;

/**
 * Command Line Interface logic
 *
 * @author Alex Pumnea
 */
public class Init {
    private static final Logger logger = LoggerFactory.getLogger(Init.class);
    private final Scanner scanner;
    private final CarController carController;
    private String outputType;
    private static final String URL = "https://999.md/ru/list/transport/cars";

    public Init() {
        scanner = new Scanner(System.in);
        Repository<Car> carRepository = new CarRepository();
        fetcherTypeMenuDisplay();
        String fetcherType = scanner.next();
        CarService carService = new CarService(carRepository, fetcherType);
        carController = new CarController(carService);
    }

    void displayMenu() {
        resultTypeMenuDisplay();
        outputType = scanner.next();
    }

    void populateDB() {
        carController.fetchData(URL);
    }

    void outputData() {
        carController.outputFetchedData(outputType);
    }


    private static void fetcherTypeMenuDisplay() {
        logger.info("""
                Select a fetcher implementation:
                1 - Class-based Singleton
                2 - Class-based Synchronized Singleton
                3 - Class-based On-Demand Holder Singleton
                4 - Enum-based Singleton
                Default: - POJO class
                Enter your choice:
                """);
    }

    private static void resultTypeMenuDisplay() {
        logger.info("""
                How results should be displayed?
                1 - Generate spreadsheet
                2 - Display in console
                Default: - Display in console
                Enter your choice:
                """);
    }
}
