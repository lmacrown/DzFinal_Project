package mock.stub;

import mock.fake.User;
import mock.fake.UserRepository;
/*
이래 코드처럼 StubUserRepository는 findById() 메서드를 사용하면 언제나 동일한 id값에 Test User라는 이름을 가진 User 인스턴스를 반환받는다.

테스트 환경에서 User 인스턴스의 name을 Test User만 받기를 원하는 경우 이처럼 동작하는 객체(UserRepository의 구현체)를 만들어 사용할 수 있다.

물론 이러한 방식의 단점은 테스트가 수정될 경우(findById() 메서드가 반환해야 할 값이 변경되야 할 경우) Stub 객체도 함께 수정해야 하는 단점이 있다.

우리가 테스트에서 자주 사용하는 Mockito 프레임워크도 Stub와 같은 역할을 해준다.

이처럼 테스트를 위해 의도한 결과만 반환되도록 하기 위한 객체가 Stub이다.
 */

public class StubUserRepository implements UserRepository {
    @Override
    public void save(User user) {
    }

    @Override
    public User findById(long id) {
        return new User(id, "Test User");
    }


}