import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;

//아래 클래스는 테스트 하지 않는다
@Disabled
class 테스트실행하지_않을때 {
    @Test
    void 테스트() {

    }
}


public class  DisabledTest {
    //아래 메서드는 테스트 하지 않는다
    @Disabled
    @Test
    void failMethod() {
        try {
            fail();
        } catch(IllegalArgumentException e) {
        }
    }

    //아래 메서드는 정상 테스트 한다
    @Test
    void 정상테스트 () {
    }
}

