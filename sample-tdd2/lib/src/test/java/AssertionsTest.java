import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.Timeout;

import java.time.Duration;
import java.util.Arrays;
import java.util.List;
import java.util.Vector;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.TimeoutException;

import static org.junit.jupiter.api.Assertions.*;

public class AssertionsTest {
    @Test
    void 일반적인_테스트() {
        assertEquals(2, 2);

        //Object.equals() 메서드 실행 하여 결과비교
        String name1 = new String("홍길동");
        String name2 = new String("홍길동");
        assertEquals(name1, name2);
    }

    @Test
    void 일반적인_문자열_테스트() {
        assertEquals(2, 2);

        //Object.equals() 메서드 실행 하여 결과비교
        String name1 = "홍길동";
        String name2 = "홍길동";
        assertEquals(name1, name2);

        name1 = new String("홍길동");
        name2 = new String("홍길동");
        //assertSame(name1, name2); //해당 실행 결과 실패
        assertEquals(name1, name2);
    }

    @Test
    void 문자열_객체가_동일한지_테스트() {
        //Object.equals() 메서드 실행 하여 결과비교
        String name1 = "홍길동";
        String name2 = name1;
        assertSame(name1, name2);
    }

    @Test
    void 여려_실행결과_테스트1() {
        assertEquals(3, 5/2); //검증 실패로 오류 발생
        System.out.println("이 코드는 실행되지 않음");
        assertEquals(4, 2 * 2); //이 코드는 실행되지 않음
    }

    @Test
    void 여려_실행결과_테스트2() {
        assertAll("person",
            () -> assertEquals(3, 5/2),
            () -> System.out.println("이 코드는 실행되지 않음"),
            () -> assertEquals(4, 2 * 2),
            () -> assertEquals(6, 11 / 2)
        );
    }

    static int devide(int a, int b) throws Exception {
        if (b == 0) throw new ArithmeticException("/ by zero");
        return a / b;
    }

    @Test
    void 예외테스트() {
        //구문 실행중 예외를 받는다 
        Exception exception = assertThrows(ArithmeticException.class,
                () -> devide(1,0));
        //예외의 내용을 확인한다
        assertEquals("/ by zero", exception.getMessage());
    }

    @Test
    @Timeout(unit = TimeUnit.MILLISECONDS, value = 100)
    void 어노테이션을_이용한_실행_시간_테스트(){
        try {
            List<String> array = Arrays.asList("123", "456", "789", "abc");
            //배열을 이용하여 어떤 작업을 시뮬레이션한 것
            //테스트 시간을 변경해 보면서 테스트 할 것
            Thread.sleep(10);
        }catch (InterruptedException ex) {
            ex.printStackTrace();
        }
    }

    @Test
    void 루틴_실행_시간_테스트(){
        assertTimeout(Duration.ofMillis(100), () -> {
            List<String> array = Arrays.asList("123", "456", "789", "abc");
            //배열을 이용하여 어떤 작업을 시뮬레이션한 것
            //테스트 시간을 변경해 보면서 테스트 할 것
            Thread.sleep(10);
        });
    }

    //내가 지정한 수행 시간보다 오래 걸리면 자동으로 테스트 실패로 종료 할 때 사용하는 방법
    //assertTimeoutPreemptively() 단언문을 사용하면됨
    @Test
    void 루틴_실행_시간_지연시_자동종료_테스트(){
        assertTimeoutPreemptively(Duration.ofMillis(100), () -> {
            List<String> array = Arrays.asList("123", "456", "789", "abc");
            //배열을 이용하여 어떤 작업을 시뮬레이션한 것
            //테스트 시간을 변경해 보면서 테스트 할 것
            Thread.sleep(200);
        });
    }
}
