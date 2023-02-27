package tdd.mock.carsale2.carsale;

import java.util.List;

public class CarSaleAggregation {
    private ICarSaleStorage storage;

    public CarSaleAggregation(ICarSaleStorage storage) {
        this.storage = storage;
    }

    public int sum() {
        List<Integer> months = storage.getMonths();
        int sum = 0;
        try {
            for (int month : months) {
                sum += storage.getValue(month);
            }
            return sum;
        }
        catch (Exception e) {
            throw e;
        }
    }

    public double average() {
        List<Integer> months = storage.getMonths();
        try {
            return (double)sum() / months.size();
        }
        catch (Exception e) {
            throw e;
        }
    }
}
