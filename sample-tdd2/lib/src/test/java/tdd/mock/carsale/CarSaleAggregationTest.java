package tdd.mock.carsale;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;


public class CarSaleAggregationTest {
    ICarSaleStorage carSaleStorage;
    CarSaleAggregation carSaleAggregation;

    @BeforeEach
    void SetUp() {
        carSaleStorage = spy(new SpyCarSaleStorage());
        carSaleAggregation = new CarSaleAggregation(carSaleStorage);

        //월 매출 정보를 추가한다
        carSaleStorage.insertValue(1, 100);
        carSaleStorage.insertValue(2, 200);
        carSaleStorage.insertValue(3, 300);
    }

    @Test
    void 합계_테스트() {
        System.out.println("Calculator 는 storage 에서 월별 매출에 대한 총합을 구할 수 있다.");
        System.out.println("storage 에 저장된 월별 매출에 대한 총합을 구한다.");

        System.out.println("1월 부터 3월까지 3개월에 대한 매출 정보가 있다.");

        System.out.println("총합을 구했을 때");

        System.out.println("모든 정보를 1번만 로딩해야 하고,");
        int sum = carSaleAggregation.sum();

        System.out.println("총합은 600 이어야 한다.");
        assertEquals(600, sum);

        //getMonths() 메서드를 1회 호출한다
        verify(carSaleStorage, times(1)).getMonths(); // success
        verify(carSaleStorage, times(3)).getValue(anyInt()); // success

    }

    @Test
    void 평균_테스트() {
        System.out.println("Calculator 는 storage 에서 월별 매출에 대한 총합을 구할 수 있다.");
        System.out.println("storage 에 저장된 월별 매출에 대한 총합을 구한다.");

        System.out.println("1월 부터 3월까지 3개월에 대한 매출 정보가 있다.");

        System.out.println("평균을 구했을 때");

        System.out.println("모든 정보를 2번만 로딩해야 하고,");
        double average = carSaleAggregation.average();

        System.out.println("총합은 600 이어야 한다.");
        assertTrue(Math.abs(200.0 - average) <= 0.0001);

        //getMonths() 메서드를 1회 호출한다
        verify(carSaleStorage, times(2)).getMonths(); // success
        verify(carSaleStorage, times(3)).getValue(anyInt()); // success

    }

}
