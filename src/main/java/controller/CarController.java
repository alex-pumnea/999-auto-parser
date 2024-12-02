package controller;

import service.CarService;

/**
 * @author Alex Pumnea
 */
public class CarController {
    private final CarService carService;

    public CarController(CarService carService) {
        this.carService = carService;
    }

    public void outputFetchedData(String type) {
        carService.outputFetchedData(type);
    }

    public void fetchData(String url) {
        carService.fetchCars(url);
    }
}
