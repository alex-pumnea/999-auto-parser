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
import service.api.SpreadsheetGenerator;
import service.exceptions.SpreadsheetException;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;
import java.util.regex.Matcher;
import java.util.regex.Pattern;


/**
 * @author Alex Pumnea
 */
public class CarService {
    private static final Logger logger = LoggerFactory.getLogger(CarService.class);

    private final Validator validator;
    private final Repository<Car> repository;
    private final Fetcher fetcher;
    private final SpreadsheetGenerator generator;

    public CarService(Repository<Car> repository, String fetcherType) {
        this.repository = repository;
        FetcherFactory fetcherFactory = new CarFetcherFactory();
        ValidatorFactory factory = Validation.buildDefaultValidatorFactory();
        this.validator = factory.getValidator();
        this.fetcher = fetcherFactory.getCarFetcher(fetcherType);
        generator = new LibreOfficeSpreadsheetGenerator();
    }

    public void fetchCars(String url) {
        repository.addAll(parseCars(url));
    }

    private List<Car> parseCars(String url) {
        List<Car> cars = new ArrayList<>();
        Elements carElements = fetcher.getCarElements(url);
        Pattern pattern = Pattern.compile("\\d+");

        for (Element carElement : carElements) {
            String makeAndYear = carElement.getElementsByClass("js-item-ad").text();

            if (validData(makeAndYear) || isBooster(carElement)) continue;

            String[] parts = makeAndYear.split(", ");
            String make = getMake(parts);
            String year = getYear(carElement, parts);
            String price = getPrice(carElement, pattern);

            Car car = new Car.Builder()
                    .setMake(make)
                    .setYear(year)
                    .setPrice(price)
                    .build();

            validateCar(car);
            cars.add(car);
        }
        return cars;
    }

    private static String getPrice(Element carElement, Pattern pattern) {
        String price = carElement.getElementsByClass("ads-list-photo-item-price-wrapper")
                .text()
                .replace(" €", "")
                .replace("$", "")
                .replaceAll("\\s", "");
        Matcher matcher = pattern.matcher(price);
        if (matcher.find()) {
            price = matcher.group();
        }
        return price;
    }

    private String getYear(Element carElement, String[] parts) {
        return parts.length > 1 ? parts[1].replace(" г.", "").trim() : fetcher.getYearFromDetails(carElement);
    }

    private static String getMake(String[] parts) {
        return parts[0].trim();
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

    public void outputFetchedData(String type) {
        if (isValidOutputType(type)) {
            if (type.equals("1")) {
                generateSpreadsheet();
            } else displayInConsole();
        } else logger.warn("Invalid output type: {}", type);
    }

    private boolean isValidOutputType(String input) {
        return input.matches("[1-2]") || input.isEmpty();
    }

    public void displayInConsole() {
        if (repository.getAll().isEmpty()) {
            logger.info("No cars found");
            return;
        }
        repository.getAll().forEach(car -> logger.info("Fetched car: {}", car));
    }

    public void generateSpreadsheet() {
        try {

            generator.generateSpreadsheet(repository.getAll());
            logger.info("Spreadsheet created successfully on desktop!");
        } catch (SpreadsheetException e) {
            logger.warn("Failed to create spreadsheet: {}", e.getMessage());
        }
    }
}
