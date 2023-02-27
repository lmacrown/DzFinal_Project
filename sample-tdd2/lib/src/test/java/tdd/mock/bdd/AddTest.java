package tdd.mock.bdd;

import org.junit.jupiter.api.Test;
import org.mockito.ArgumentCaptor;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.BDDMockito.*;
import static org.assertj.core.api.Assertions.*;

interface Calculator {
    int add(int a, int b);
    int div(int a, int b);
}

public class AddTest {

    //TDD의 방식의 Mock 테스트 코드
    @Test
    void TDD기반의_계산기_add_테스트() {
        Calculator calculator = mock(Calculator.class);

        //given
        when(calculator.add(10,20)).thenReturn(30);

        //when & then
        assertEquals(30, calculator.add(10,20));

        //검증 : 메서드 1회 호출
        verify(calculator.add(10, 20), times(1));
    }

    //BDD의 방식의 Mock 테스트 코드
    @Test
    void BDD기반의_계산기_add_테스트() {
        Calculator calculator = mock(Calculator.class);

        //given
        given(calculator.add(10, 20)).willReturn(30);

        //when
        int result = calculator.add(10, 20);

        //단언 확인
        assertEquals(30, result);

        //검증 : 메서드 1회 호출
        verify(calculator.add(10, 20), times(1));
    }

    //mock과 BDD와 assertJ 방식의 테스트 코드
    @Test
    void BDD기반의_assertJ_계산기_add_테스트() {
        Calculator calculator = mock(Calculator.class);

        //given
        given(calculator.add(10, 20)).willReturn(30);

        //실행
        int result = calculator.add(10, 20);

        //단언 확인
        assertThat(result).isEqualTo(30);

        //검증 : add() 메서드가 1번 호출 됐는지 검증한다.
        then(calculator).should().add(10, 20);

    }

    //mock과 BDD 방식으로 테스트 코드
    @Test
    void BDD기반의_계산기_div_테스트() {
        Calculator calculator = mock(Calculator.class);

        //given
        /* 예외 설정시
        1. willThrow() 메서드에 예외 클래스를 설정한다
        2. given()메서드에 호출할 클래스의 객체를 설정한다
        3. 실제 호출할 메서드와 전달할 인자를 설정한다
        */
        willThrow(ArithmeticException.class).given(calculator).div(10, 0);

        //when
        /* 메서드 호출 시 예외를 assertJ 패키지를 이용하여 처리하는 절차
        1. assertThrows() 메서드를 호출할 때 발생할 예외 클래스명와 실행을 위한 코드를 람구 구문으로 실행한다
        2. 실행 결과 중 예외 발생 객체를 리턴으로 받는다
         */
        Throwable thrown = assertThrows(Exception.class, () -> calculator.div(10, 0));

        //THEN
        //예외 클래스가 내가 원하는 클래스 인지 확인
        assertTrue(thrown instanceof ArithmeticException);

    }

    //mock과 BDD와 assertJ 방식의 테스트 코드
    @Test
    void BDD기반의_assertJ_계산기_div_테스트() {
        Calculator calculator = mock(Calculator.class);

        //given
        /* 예외 설정시
        1. willThrow() 메서드에 예외 클래스를 설정한다
        2. given()메서드에 호출할 클래스의 객체를 설정한다
        3. 실제 호출할 메서드와 전달할 인자를 설정한다
        */
        willThrow(ArithmeticException.class).given(calculator).div(10, 0);

        //when
        /* 메서드 호출 시 예외를 assertJ 패키지를 이용하여 처리하는 절차
        1. catchThrowable() 메서드를 호출할 때 실행을 위한 코드를 람구 구문으로 실행한다
        2. 실행 결과 중 예외 발생 객체를 리턴으로 받는다
         */
        Throwable thrown = catchThrowable(() -> calculator.div(10, 0) );

        //THEN
        //확인
        assertThat(thrown).isInstanceOf(ArithmeticException.class);
    }

    //mock과 BDD와 assertJ 방식의 테스트 코드
    @Test
    void 인자매칭방법_테스트() {
        Calculator calculator = mock(Calculator.class);

        //given
        given(calculator.add(10, 0)).willReturn(10);

        //when
        //호출 하기위해 설정한 인자와 다른 값을 설정하면
        // return type이 기본형이면 0, Object type 이면 null을 리턴한다
        int result = calculator.add(10, 10);
        assertThat(result).isEqualTo(10);
    }

    //mock과 BDD와 assertJ 방식의 테스트 코드
    @Test
    void 인자매칭방법_하나만_임의의_값설정_다른하나는_값지정_테스트() {
        Calculator calculator = mock(Calculator.class);

        //given
        //인자중 한개라도 ArgumentMatchers를 이용하여 설정하면 다른 모든 인자도 ArgumentMatchers을 사용하여 설정해야 한다
        //그러지 않으면 내부에서 NullPointerException 예외가 발생된다
        //given(calculator.add(anyInt(), 10)).willReturn(10);
        //실행 후 아래 코드로 실행해야한다
        given(calculator.add(anyInt(), eq(10))).willReturn(10);

        //when
        //호출 하기위해 설정한 인자와 다른 값을 설정하면
        // return type이 기본형이면 0, Object type 이면 null을 리턴한다
        int result = calculator.add(0, 10);
        assertThat(result).isEqualTo(10);
        result = calculator.add(10, 10);
        assertThat(result).isEqualTo(10);
    }

    //mock과 BDD와 assertJ 방식의 테스트 코드
    @Test
    void 행위검증_기본_일회_호출_테스트() {
        Calculator calculator = mock(Calculator.class);

        //given
        given(calculator.add(10, 10)).willReturn(20);

        //when
        int result = calculator.add(10, 10);

        //단언 확인
        assertThat(result).isEqualTo(20);

        //then
        //1회만 호출한다
        then(calculator).should(only()).add(10, 10);
    }

    //mock과 BDD와 assertJ 방식의 테스트 코드
    @Test
    void 행위검증_기본_2회_호출_테스트() {
        Calculator calculator = mock(Calculator.class);

        //given
        given(calculator.add(anyInt(), eq(10))).willReturn(10);

        //when
        int result = calculator.add(10, 10);
        //단언 확인
        assertThat(result).isEqualTo(10);

        result = calculator.add(0, 10);
        //단언 확인
        assertThat(result).isEqualTo(10);

        //then
        //2회만 호출
        then(calculator).should(times(2)).add(anyInt(), anyInt());
    }


    //mock과 BDD와 assertJ 방식의 테스트 코드
    @Test
    void 행위검증_add_1회_호출_div_0회_호출_테스트() {
        Calculator calculator = mock(Calculator.class);

        //given
        given(calculator.add(anyInt(), eq(10))).willReturn(10);

        //when
        int result = calculator.add(10, 10);
        //단언 확인
        assertThat(result).isEqualTo(10);

        //then
        //1회만 호출
        then(calculator).should(times(1)).add(anyInt(), anyInt());
        //div() 메서드는 절대 호출되지 않는다
        then(calculator).should(times(0)).div(anyInt(), anyInt());
        then(calculator).should(never()).div(anyInt(), anyInt());
    }

    //메일 발송 인터페이스
    interface EmailNotifier {
        void sendRegisterEmail(String email);
    }

    //회원 가입 관려 클래스
    static class UserRegister {
        private EmailNotifier emailNotifier;

        public UserRegister(EmailNotifier emailNotifier) {
            this.emailNotifier = emailNotifier;
        }

        public void register(String id, String pw, String email) {
            //비번번호 검증

            //아이디 존재여부 확인

            //신구 회원 가입시 메일 발송
            emailNotifier.sendRegisterEmail(email + "111");
        }
    }

    //mock과 BDD와 assertJ 방식의 테스트 코드
    @Test
    void 인자캡쳐_테스트() {
        EmailNotifier emailNotifier = mock(EmailNotifier.class);
        UserRegister userRegister = new UserRegister(emailNotifier);

        userRegister.register("id", "pwd", "email@email.com");

        //register() 함수내부에 호출되는 emailNotifier.sendRegisterEmail(String email)
        //메소드에 전달되는 인자값을 확인 할 때 사용하는 방법
        //1. sendRegisterEmail() 메서드의 인자로 전달되는 type, 즉 String을 이용하여 ArgumentCaptor<String> 객체를 생성한다
        //2. 함수호출을 확인하는 then() 이용하여 함수 호출시 전달되는 인자를 캡쳐 할수 있게
        // sendRegisterEmail()의 인자에 captor.capture()를 전달한다
        //3. register() 함수내부에 호출되는 emailNotifier.sendRegisterEmail(String email)의 인자를 확인 할 수 있다
        //인터페이스로 전달되는 메서드의 인자값을 확인할 때 사용할 수 있다

        ArgumentCaptor<String> captor = ArgumentCaptor.forClass(String.class);
        then(emailNotifier).should().sendRegisterEmail(captor.capture());

        String email = captor.getValue();
        //mockito 단언 구문 이용하여 확인
        assertEquals("email@email.com", email);
        //assertJ 단언 구문 이용하여 확인
        assertThat(email).isEqualTo("email@email.com");

    }

}
