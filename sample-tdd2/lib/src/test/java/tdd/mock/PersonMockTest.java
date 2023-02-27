package tdd.mock;

import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;


public class PersonMockTest {

    //mock() 메서드 호출 예제
    @Test
    public void mockMethodTest(){
        //Person p = new PersonImpl();
        //위코드는 mock 을 이용할 때 아래와 같음
        Person p = mock(Person.class);
        assertTrue( p != null );
    }

    //when() 메서드 호출 예제
    @Test
    public void mockWhenTest(){
        Person p = mock(Person.class);

        //getName() 메서드를 호출 하면 리턴은 "홍길동"을 리턴한다
        when(p.getName()).thenReturn("홍길동");
        when(p.getAge()).thenReturn(20);

        assertTrue("홍길동".equals(p.getName()));
        assertTrue(20 == p.getAge());
    }

    //when() 메서드 호출 예제
    // 매개변수가 어떤 값이라고 상관 없을 때 anyString(), anyInt(),
    // anyXXX() 로 시작하는 메소드를 사용합니다.
    // 특정 값을 사용하고 싶을 경우 when(mockIns.getList(eq("홍길동"), anyInt()))

    @Test
    public void 임의의값_전달_취미_목록을_얻어_테스트(){
        //상활 설정
        //연령대 취미 객체 생성
        AgeHobbies ageHobbies = mock(AgeHobbies.class);

        //getHobbyList() 메서드가 호출된 후 리턴될 값을 설정한다
        when(ageHobbies.getHobbyList(anyString(), anyInt())).thenReturn(
           Arrays.asList("게임", "낮잠", "영화감상")
        );

        //호출
        List<String> hobbyList = ageHobbies.getHobbyList("홍길동", 20);
        for(String hobby : hobbyList) {
            System.out.println(hobby);
        }
    }

    /*
    * 특정값을 설정하면 메서드 호출시 특정값을 전달해야 정확하게 실행된다
    * 특정값을 설정하고 다른 값을 전달해서 메서드 호출시 설정한 리턴값을 얻을 수 없다
    * */
    @Test
    public void 특정값_전달_취미_목록을_얻어_테스트(){
        //상활 설정
        //연령대 취미 객체 생성
        AgeHobbies ageHobbies = mock(AgeHobbies.class);

        //getHobbyList() 메서드가 호출된 후 리턴될 값을 설정한다
        when(ageHobbies.getHobbyList(eq("홍길동"), anyInt())).thenReturn(
                Arrays.asList("게임", "낮잠", "영화감상")
        );

        //호출
        //List<String> hobbyList = ageHobbies.getHobbyList("홍길동2", 20);
        List<String> hobbyList = ageHobbies.getHobbyList("홍길동", 20);
        for(String hobby : hobbyList) {
            System.out.println(hobby);
        }
    }

    //메서드 호출시 예외 발생할 수 있게 설정
    @Test
    public void mockThrowExceptionTest(){
        Person p = mock(Person.class);

        doThrow(new IllegalArgumentException()).when(p).setName(eq("홍길동"));

        // 해당 메소드를 호출할 경우 선언된 예외 발생
        assertThrows(IllegalArgumentException.class, ()-> p.setName("홍길동"));

        //홍길동이 아니면 예외를 발생하지 않는다
        assertDoesNotThrow(()-> p.setName("이순신"));
    }

    /*noNothing()
     void로 선언된 메소드에 when()을 걸고 싶을 때 사용하는 메소드입니다.

     결론적으로 void로 선언된 메소드에 when()을 걸고 싶을 때 사용하는 방법은 (doNothing(), doThrow()) 입니다.
     */
    @Test
    public void mockDoNothingTest1(){
        Person p = mock(Person.class);
        // when에 대한 반환이 없음
        doNothing().when(p).setAge(anyInt());
        p.setAge(20);
        //setAge() 메서드호출 확인
        verify(p).setAge(anyInt());
    }

    /*
    verify()
    해당 구문이 호출되었는지 체크합니다. 단순 호출뿐만 아니라, 횟수나 타임아웃시간까지 지정하여 체크할 수 있습니다.

    검증 방법
    1. 검증에 필요한 메서드를 실행 하고
    2. 이후에 검증 대상 메서드의 실행 호출 상황을 체크 한다.

    */
    @Test
    public void mockVerifyTest(){
        Person p = mock(Person.class);
        String name = "홍길동";
        // 1번 호출한다
        p.setName(name);
        // 1번 호출했는지 체크
        verify(p, times(1)).setName(any(String.class)); // success

        // 호출 안했는지 체크
        verify(p, never()).getName(); // success
        verify(p, never()).setName(eq("ETC")); // success
        verify(p, never()).setName(eq("이순신")); // success
        //verify(p, never()).setName(eq("홍길동")); // fail

        // 최소한 1번 이상 호출했는지 체크
        verify(p, atLeastOnce()).setName(any(String.class)); // success
        // 2번 이하 호출 했는지 체크
        verify(p, atMost(2)).setName(any(String.class)); // success
        // 2번 이상 호출 했는지 체크
        verify(p, atLeast(2)).setName(any(String.class)); // fail
        // 지정된 시간(millis)안으로 메소드를 호출 했는지 체크
        verify(p, timeout(100)).setName(any(String.class)); // success
        // 지정된 시간(millis)안으로 1번 이상 메소드를 호출 했는지 체크
        verify(p, timeout(100).atLeast(1)).setName(any(String.class)); // success
    }

    /*
    @Mock
    mock() 메소드 외에도 목 객체를 만들기 위해 @Mock 어노테이션을 선언하는 방법

    MockitoAnnotations.openMocks(this) 메서드를 호출해줘야 framework에서 mock 객체를 생성하여 리턴한다

    */
    @Mock
    Person mockPerson1;
    @Mock
    Person mockPerson2;

    @Test
    public void mockAnnotationTest(){
        //mockPerson1, mockPerson2에 객체 생성되어 대입되기 때문에 null일 수 없다
        //아래 메서드 호출에 의하여 @Mock로 지정된 참조변수에 객체를 생성하여 대입해 준다
        MockitoAnnotations.openMocks(this);

        assertTrue(mockPerson1 != null);
        assertTrue(mockPerson2 != null);
    }

}
