import static org.junit.jupiter.api.Assertions.*;
import org.junit.jupiter.api.Test;

public class TestClassExam {

    @Test
    void 테스트클래스예제() {

    }
}

class TestStaticMemberClassExam {

    static String getString() {
        return "abcde";
    }

    @Test
    void 테스트정적멤버클래스예제() {
        String str = getString();
        assertEquals("abcde", str);
    }
}

//아래 클래스는 테스트 클래스아님
class NoTestClass {
    static  String getString() {
        return "abcdef";
    }
}

