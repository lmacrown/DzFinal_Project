import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.condition.DisabledOnOs;
import org.junit.jupiter.api.condition.EnabledOnOs;
import org.junit.jupiter.api.condition.OS;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

//사용자 정의 테스트 어노테이션 선언
@Target(ElementType.METHOD)
@Retention(RetentionPolicy.RUNTIME)
@Test
@EnabledOnOs(OS.WINDOWS)
@interface TestOnWindows {
}

public class ConditionalTest {
    @Test
    @EnabledOnOs(OS.MAC)
    void onlyOnMacOs() {
// ...
    }

    //오직 윈도우 환경에서만 테스트 합니다
    @TestOnWindows
    void testOnWindows() {
// ...
    }

    //오직 리눅스와 윈도우 환경에서만 테스트 합니다
    @Test
    @EnabledOnOs({OS.LINUX, OS.WINDOWS})
    void onLinuxOrWindows() {
// ...
    }

    @Test
    @DisabledOnOs(value = OS.MAC, disabledReason = "맥에서는 테스트하지 않아요.")
    void testNotOnMac() {
// ...
    }

}