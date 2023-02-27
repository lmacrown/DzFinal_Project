package tdd.mock.carsale2.carsale;

import java.util.List;

public interface ICarSaleStorage {
    List<Integer> getMonths();
    int getValue(int month);
    void insertValue(int month, int value);
    void updateValue(int month, int value);
    void removeMonth(int month);
}
