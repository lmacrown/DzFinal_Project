import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.condition.DisabledIf;
import org.junit.jupiter.api.condition.EnabledIf;

/*
@EnabledIf 나 @DisabledIf 를 클래스 레벨에 사용할 때 조건 메서드는 반드시 static 으로 선언되어야 한다.
또한 외부 클래스에 위치한 조건 메서드도 static 으로 선언되어야 하며,
사용할 때는 패키지를 포함한 전체 이름을 적어줘야 한다.
*/

public class CustomConditionalTest {
    @Test
    @EnabledIf("ExternalClass#eCustomCondition")
    void enabled() {
        System.out.println("test enabled active");
    }

    @Test
    @DisabledIf("customCondition")
    void disabled() {
    }
    boolean customCondition() {
        return true;
    }
}
//조건 클래스
class ExternalClass {
    //조건 메서드 반드시 static 으로 구현 할 것, 객체 생성 없이 호출 해야 할 수 있어여 함
    static boolean customCondition() {
        return true;
    }
}