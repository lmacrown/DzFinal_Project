package mock.fake;

import java.util.ArrayList;
import java.util.Collection;
/*
테스트해야 하는 객체가 데이터베이스와 연관되어 있다고 가정해보자.
그럴 경우 실제 데이터베이스를 연결해서 테스트해야 하지만,
실제 데이터베이스 대신 가짜 데이터베이스 역할을 하는 FakeUserRepository를 만들어 테스트 객체에 주입하는 방법도 있다.
이렇게 하면 테스트 객체는 데이터베이스에 의존하지 않으면서도 동일하게 동작을 하는 가짜 데이터베이스를 가지게 된다.
이처럼 실제 객체와 동일한 역할을 하도록 만들어 사용하는 객체가 Fake이다.
*/

public class FakeUserRepository implements UserRepository {
    private Collection<User> users = new ArrayList<>();

    @Override
    public void save(User user) {
        if (findById(user.getId()) == null) {
            users.add(user);
        }
    }

    @Override
    public User findById(long id) {
        for (User user : users) {
            if (user.getId() == id) {
                return user;
            }
        }
        return null;
    }
}