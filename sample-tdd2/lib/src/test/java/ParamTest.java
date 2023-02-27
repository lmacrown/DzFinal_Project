import org.junit.jupiter.api.*;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;

import static org.junit.jupiter.api.Assertions.*;

public class ParamTest {

    @DisplayName("파라미터 테스트")
    @ParameterizedTest
    @ValueSource(strings = {"테스트시", "값이", "많아", "어려워지고", "있네요."})
    void 파라미터_테스트(String message){
        System.out.println(message);
    }
}
