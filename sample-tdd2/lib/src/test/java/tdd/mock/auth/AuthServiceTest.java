package tdd.mock.auth;

import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.mockito.Spy;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

public class AuthServiceTest {
    @Mock
    AuthDAO authDAO;

    @InjectMocks
    AuthService service;

    @Test
    void mockObjectTest() {
        MockitoAnnotations.openMocks(this);

        assertTrue(service != null);
        assertTrue(service.getAuthDAO() != null);
    }

    @Test
    void 로그인여부확인테스트() {
        MockitoAnnotations.openMocks(this);

        when(authDAO.isLogin(eq("hong"))).thenReturn(true);

        assertTrue(service.isLogin("hong") == true);
        assertTrue(service.isLogin("lee") == false);
    }

    /*
    spy() : 인터페이스가 아닌 실제 객체를 Mock로 만든 것을 spy 라고 한다
    Spy 객체는 실제 객체처럼 동작하고, verify로 어떤 메소드가 호출되었는지 확인할 수 있습니다.
    또한, when 키워드로 일부 메소드를 stubbing할 수 있습니다.
    다음과 같이 spy 객체를 만들 수 있습니다.
    실행해보면 테스트는 pass되며 실제 객체처럼 동작한다는 것을 알 수 있습니다.
    */
    @Test
    void 스파이_객체_테스트() {
        List<String> spyList = spy(ArrayList.class);

        spyList.add("사과");
        assertEquals("사과", spyList.get(0));
    }

    //다음과 같이 verify로 메소드가 호출되었는지 검증할 수 있습니다.
    @Test
    void 스파이_객체_메서드호출_검증_테스트() {
        List<String> spyList = spy(ArrayList.class);

        spyList.add("사과");
        verify(spyList).add("사과");

        spyList.get(0);
        verify(spyList).get(0);
    }

    //다음과 같이 when으로 일부 메소드를 stubbing할 수 있습니다.
    // 하지만 아래 코드는 문제가 있습니다.
    @Test
    public void 스파이객체_when_테스트() {
        List spyList = spy(ArrayList.class);
        //사전에 값을 추가하면 아래 get()에 대하여 설정할 수 있음 
        //만약 아래 코드 실행 안되면 테스트 실패됨 : IndexOutOfBoundsException예외가 발생하면서 테스트는 실패합니다
        spyList.add("");
        spyList.add("");

        when(spyList.get(0)).thenReturn("사과");
        when(spyList.get(1)).thenReturn("키위");
        when(spyList.size()).thenReturn(10);

        assertEquals("사과", spyList.get(0));
        assertEquals("키위", spyList.get(1));
        assertEquals(10, spyList.size());
    }

    //doReturn().when() 패턴으로 변경해주면 성공적으로 stubbing할 수 있습니다.
    @Test
    public void 스파이객체를_이용하여_doReturn_테스트() {
        List spyList = spy(ArrayList.class);

        doReturn("사과").when(spyList).get(0);
        doReturn("키위").when(spyList).get(1);
        doReturn(10).when(spyList).size();

        assertEquals("사과", spyList.get(0));
        assertEquals("키위", spyList.get(1));
        assertEquals(10, spyList.size());
    }
}
