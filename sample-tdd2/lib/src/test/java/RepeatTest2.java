import org.junit.jupiter.api.*;
import static org.junit.jupiter.api.Assertions.*;

public class RepeatTest2 {

    @BeforeEach
    void beforeEach(TestInfo testInfo, RepetitionInfo repetitionInfo) {
        int currentRepetition = repetitionInfo.getCurrentRepetition();
        int totalRepetitions = repetitionInfo.getTotalRepetitions();

        String methodName = testInfo.getTestMethod().get().getName();
        System.out.println(String.format("반복실행 정보 현재 실행 횟수 : %d , 전체 실행 횟수 : %d , 실행 메소드 명 : %s",
                currentRepetition, totalRepetitions, methodName));
    }

    @RepeatedTest(10)
    void 기본반복테스트() {
    }

    @RepeatedTest(5)
    void 반복정보를_이용한_반복테스트(RepetitionInfo repetitionInfo) {
        assertEquals(5, repetitionInfo.getTotalRepetitions());
    }

    @RepeatedTest(value = 1, name = "{displayName} {currentRepetition}/{totalRepetitions}")
    @DisplayName("반복!")
    void customDisplayName (TestInfo testInfo) {
        assertEquals("반복! 1/1", testInfo.getDisplayName());
    }

    @RepeatedTest(value = 1, name = RepeatedTest.LONG_DISPLAY_NAME)
    @DisplayName("반복상세...")
    void customDisplayNameWithLongPattern(TestInfo testInfo) {
        assertEquals("반복상세... :: repetition 1 of 1", testInfo.getDisplayName());
    }

    @DisplayName("반복 커스트마이징")
    @RepeatedTest(value = 5, name = "[{displayName}] , {currentRepetition} / {totalRepetitions}")
    void 반복시_실행_정보_커스마이징() { // ...
    }
}
