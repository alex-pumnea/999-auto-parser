package cli;

import controller.CarController;
import model.Car;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import repository.CarRepository;
import repository.Repository;
import service.CarService;

import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

/**
 * Command Line Interface logic
 *
 * @author Alex Pumnea
 */
public class Cli {
    private static final Logger logger = LoggerFactory.getLogger(Cli.class);
    private static final String URL = "https://999.md/ru/list/transport/cars";
    private final List<Car> cars = new ArrayList<>();

    void displayMenu() {
        logger.info("Select a fetcher implementation:");
        logger.info("1 - Class-based Singleton");
        logger.info("2 - Class-based Synchronized Singleton");
        logger.info("3 - Class-based On-Demand Holder Singleton");
        logger.info("4 - Enum-based Singleton");
        logger.info("Default: - POJO class");
        logger.info("Enter your choice: ");
    }


    void initApp() {
        try (Scanner scanner = new Scanner(System.in)) {
            String fetcherType = scanner.next();
            Repository<Car> carRepository = new CarRepository();
            CarService carService = new CarService(carRepository, fetcherType);
            CarController carController = new CarController(carService);
            cars.addAll(carController.getAll(URL));
        }
    }

    void displayResults() {
        if (cars.isEmpty()) {
            logger.info("No cars found");
            return;
        }
        cars.forEach(car -> logger.info("Fetched car: {}", car));
    }
}
