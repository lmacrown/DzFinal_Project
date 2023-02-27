package mock.fake;

public interface UserRepository {
    void save(User user);
    User findById(long id);
}
