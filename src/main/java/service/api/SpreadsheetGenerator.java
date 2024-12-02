package service.api;

import model.Car;
import service.exceptions.SpreadsheetException;

import java.util.List;

public interface SpreadsheetGenerator {
    void generateSpreadsheet(List<Car> cars) throws SpreadsheetException;
}