package repository;

import model.Car;

import java.util.ArrayList;
import java.util.List;

/**
 * @author Alex Pumnea
 */
public class CarRepository implements Repository<Car> {
    private static final List<Car> cars = new ArrayList<>();


    @Override
    public List<Car> getAll() {
        return cars;
    }

    @Override
    public void add(Car car) {
        cars.add(car);
    }

    @Override
    public void addAll(List<Car> t) {
        cars.addAll(t);
    }
}
