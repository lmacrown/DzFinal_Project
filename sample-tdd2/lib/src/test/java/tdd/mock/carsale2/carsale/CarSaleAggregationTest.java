package tdd.mock.carsale2.carsale;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.Arrays;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.Mockito.*;

/*
ICarSaleStorage에 대한 mock 객체를 생성하여
getMonths(), getValue() 메서드의 호출에 대한 리턴 값을 설정한다

CarSaleAggregation.sum()과 CarSaleAggregation.average() 메서드의 구현을 테스트 한다
 */

public class CarSaleAggregationTest {
    ICarSaleStorage carSaleStorage;
    CarSaleAggregation carSaleAggregation;

    @BeforeEach
    void SetUp() {
        carSaleStorage = mock(ICarSaleStorage.class);
        carSaleAggregation = new CarSaleAggregation(carSaleStorage);

        //달에 목록을 대한 리턴 값을 설정한다
        when(carSaleStorage.getMonths()).thenReturn(Arrays.asList(1,2,3));
        when(carSaleStorage.getValue(1)).thenReturn(100);
        when(carSaleStorage.getValue(2)).thenReturn(200);
        when(carSaleStorage.getValue(3)).thenReturn(300);

    }

    @Test
    void 합계_테스트() {
        System.out.println("Calculator 는 storage 에서 월별 매출에 대한 총합을 구할 수 있다.");
        System.out.println("storage 에 저장된 월별 매출에 대한 총합을 구한다.");

        System.out.println("1월 부터 3월까지 3개월에 대한 매출 정보가 있다.");
        //달에 목록을 대한 리턴 값을 설정한다
//        when(carSaleStorage.getMonths()).thenReturn(Arrays.asList(1,2,3));
//        when(carSaleStorage.getValue(1)).thenReturn(100);
//        when(carSaleStorage.getValue(2)).thenReturn(200);
//        when(carSaleStorage.getValue(3)).thenReturn(300);
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
        //달에 목록을 대한 리턴 값을 설정한다
//        when(carSaleStorage.getMonths()).thenReturn(Arrays.asList(1,2,3));
//        when(carSaleStorage.getValue(1)).thenReturn(100);
//        when(carSaleStorage.getValue(2)).thenReturn(200);
//        when(carSaleStorage.getValue(3)).thenReturn(300);

        System.out.println("평균을 구했을 때");

        System.out.println("모든 정보를 2번만 로딩해야 하고,");

        double average = carSaleAggregation.average();

        System.out.println("총합은 600 이어야 한다. " + average);
        assertTrue(Math.abs(200.0 - average) <= 0.0001);

        //getMonths() 메서드를 1회 호출한다
        verify(carSaleStorage, times(2)).getMonths(); // success
        verify(carSaleStorage, times(3)).getValue(anyInt()); // success

    }

}
