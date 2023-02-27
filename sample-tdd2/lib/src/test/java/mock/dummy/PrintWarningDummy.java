package mock.dummy;

/*
실제 객체는 PrintWarning 인터페이스의 구현체를 필요하지만,
특정 테스트에서는 해당 구현체의 동작이 전혀 필요하지 않을 수 있다.
실제 객체가 로그용 경고만 출력한다면 테스트 환경에서는 전혀 필요 없기 때문이다.
이런 경우에는 print() 가 아무런 동작을 하지 않아도 테스트에는 영향을 미치지 않는다.
이처럼 동작하지 않아도 테스트에는 영향을 미치지 않는 객체를 Dummy 객체라고 한다.
 */
public class PrintWarningDummy implements PringWarning  {

    @Override
    public void print() {
        // 아무런 동작을 하지 않는다.
    }
}
