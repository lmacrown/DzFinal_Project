import org.junit.jupiter.api.RepeatedTest;

public class RepeatTest {

    //아래 테스트 함수를 10회 반복 실행한다
    @RepeatedTest(10)
    void 반복테스트(){
        System.out.println("test");
    }

}
