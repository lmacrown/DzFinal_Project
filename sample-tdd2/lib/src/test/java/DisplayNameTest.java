import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

@DisplayName("특수한 케이스 테스트")
class DisplayNameTest {

    @DisplayName("커스텀")
    @Test
    void funcTest() {
    	System.out.println("funcTest");
    }

    @Test
    @DisplayName("°□°")
    void 특수문자테스트() {
    	System.out.println("특수문자테스트");
    }

    @Test
    @DisplayName("😭")
    void 이모지테스트() {
    	System.out.println("이모지테스트");
    }
}