package controller;

import model.Car;
import service.CarService;

import java.util.List;

/**
 * @author Alex Pumnea
 */
public class CarController {
    private final CarService carService;

    public CarController(CarService carService) {
        this.carService = carService;
    }

    public List<Car> getAll(String url) {
        return carService.getAll(url);
    }
}
