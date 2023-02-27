package tdd.mock.carsale;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class SpyCarSaleStorage implements ICarSaleStorage {
    private Map<Integer, Integer> storage = new HashMap<>();

    @Override
    public List<Integer> getMonths() {
        List<Integer> result = new ArrayList<>();

        for (Map.Entry<Integer, Integer> entry : storage.entrySet()) {
            result.add(entry.getKey());
        }
        return result;
    }

    @Override
    public int getValue(int month) {
        return storage.get(month);
    }

    @Override
    public void insertValue(int month, int value) {
        storage.put(month, value);
    }

    @Override
    public void updateValue(int month, int value) {
        storage.put(month, value);
    }

    @Override
    public void removeMonth(int month) {
        storage.remove(month);
    }
}
