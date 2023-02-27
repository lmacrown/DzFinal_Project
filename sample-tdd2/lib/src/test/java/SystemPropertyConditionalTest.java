import org.junit.jupiter.api.*;
import org.junit.jupiter.api.condition.DisabledIfSystemProperty;
import org.junit.jupiter.api.condition.EnabledIfSystemProperty;

/*
Junit Jupiter 5.6부터는 EnabledIfSystemProperty 와 DisabledIfSystemProperty 는 반복가능한 어노테이션(@RepeatedTest) 으로 변경되었다.
 그 결과로, 테스트를 할 때 여러 개를 중첩해서 사용할 수 있다.
*/

public class SystemPropertyConditionalTest {
    @Test
    @EnabledIfSystemProperty(named = "os.arch", matches = ".*64.*")
    void onlyOn64BitArchitectures() {
// ...
    }

    //사용자 언어가 일본어 면 실행 하지 않는다
    @Test @DisabledIfSystemProperty(named = "user.language", matches = "jp")
    void userLanguageKorea() {
// ...
    }

    public static void main(String[] args) {
        //JVM 시스템 속성값을 읽어 화면에 출력한다
        System.getProperties().list(System.out);
    }
}