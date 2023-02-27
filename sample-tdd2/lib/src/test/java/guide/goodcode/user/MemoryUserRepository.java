package guide.goodcode.user;

import java.util.HashMap;
import java.util.Map;

public class MemoryUserRepository implements UserRepository {
    private Map<String, User> users = new HashMap<>();

    @Override
    public void save(User user) {
        if (users.containsKey(user.getId())) {
            throw new DupIdException();
        }
        users.put(user.getId(), user);
    }

    @Override
    public User findById(String id) {
        return users.get(id);
    }
}
