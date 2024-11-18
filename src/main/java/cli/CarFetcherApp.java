package cli;

import controller.CarController;
import model.Car;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import repository.CarRepository;
import repository.Repository;
import service.CarFetcher;
import service.CarService;
import service.Fetcher;

/**
 * @author Alex Pumnea
 */
public class CarFetcherApp {
    private static final Logger logger = LoggerFactory.getLogger(CarFetcherApp.class);

    public static void main(String[] args) {
        Repository<Car> carRepository = new CarRepository();
        Fetcher fetcher = new CarFetcher();
        CarService carService = new CarService(carRepository, fetcher);
        CarController carController = new CarController(carService);
        String url = "https://999.md/ru/list/transport/cars";
        carController.getAll(url).forEach(car -> logger.info("Fetched car: {}", car));
    }
}
