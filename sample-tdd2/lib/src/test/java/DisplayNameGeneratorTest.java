import org.junit.jupiter.api.*;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;
import static org.junit.jupiter.api.Assertions.*;

//@DisplayNameGeneration(DisplayNameGenerator.Simple.class)
@DisplayNameGeneration(DisplayNameGenerator.Standard.class)
public class DisplayNameGeneratorTest {
    @Test
    void 밑줄_문자_공배으로_출력(){
        assertEquals(1,1);
    }

    @Test
    @DisplayName("이름을_변경하여_출력됨")
    void nameTest(){
        assertEquals(1,1);
    }

    @Nested//또다른 클래스를 만들 수 있다
    @IndicativeSentencesGeneration(separator = " -> ", generator = DisplayNameGenerator.ReplaceUnderscores.class)
    class X년은_윤년이다 {

        @ParameterizedTest(name = "{0} 년은 윤년이다.")
        @ValueSource(ints = {2016, 2020, 2048})
        void 다음의_값들은_윤년이다(int year) {
        }

    }

}

