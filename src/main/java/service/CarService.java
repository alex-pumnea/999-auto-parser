package service;

import factory.CarFetcherFactory;
import factory.FetcherFactory;
import jakarta.validation.ConstraintViolation;
import jakarta.validation.Validation;
import jakarta.validation.Validator;
import jakarta.validation.ValidatorFactory;
import model.Car;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import repository.Repository;
import service.api.Fetcher;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;


/**
 * @author Alex Pumnea
 */
public class CarService {
    private static final Logger logger = LoggerFactory.getLogger(CarService.class);

    private final Validator validator;
    private final Repository<Car> repository;
    private final Fetcher fetcher;

    public CarService(Repository<Car> repository, String fetcherType) {
        this.repository = repository;
        FetcherFactory fetcherFactory = new CarFetcherFactory();
        ValidatorFactory factory = Validation.buildDefaultValidatorFactory();
        this.validator = factory.getValidator();
        this.fetcher = fetcherFactory.getCarFetcher(fetcherType);
    }

    public void addFetchedCars(String url) {
        List<Car> cars = parseCars(url);
        repository.addAll(cars);
    }

    private List<Car> parseCars(String url) {
        List<Car> cars = new ArrayList<>();
        Elements carElements = fetcher.getCarElements(url);

        for (Element carElement : carElements) {
            String makeAndYear = carElement.getElementsByClass("js-item-ad").text();

            if (validData(makeAndYear) || isBooster(carElement)) continue;

            String[] parts = makeAndYear.split(", ");
            String make = parts[0].trim();
            String year = parts.length > 1 ? parts[1].replace(" г.", "").trim() : fetcher.getYearFromDetails(carElement);
            String price = carElement.getElementsByClass("ads-list-photo-item-price-wrapper")
                    .text()
                    .replace(" €", "")
                    .replace("$", "")
                    .replaceAll("\\s", "");
            Car car = new Car(make, year, price);
            validateCar(car);
            cars.add(car);
        }
        return cars;
    }

    private static boolean isBooster(Element carElement) {
        return carElement.hasClass("js-booster-inline");
    }

    private static boolean validData(String makeAndYear) {
        return makeAndYear.isEmpty();
    }

    private void validateCar(Car car) {
        Set<ConstraintViolation<Car>> violations = validator.validate(car);
        if (!violations.isEmpty()) {
            violations.forEach(v -> logger.warn("Validation failed for car '{}': {}", car, v.getMessage()));
        }
    }

    public List<Car> getAll(String url) {
        addFetchedCars(url);
        return repository.getAll();
    }
}
