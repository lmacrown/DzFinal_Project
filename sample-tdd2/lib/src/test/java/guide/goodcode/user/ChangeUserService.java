package guide.goodcode.user;

public class ChangeUserService {
    private UserRepository userRepository;

    public ChangeUserService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    public void changeAddress(String uid, Address address) {
        User user = userRepository.findById(uid);
        if (user == null) {
            throw new UserNotFoundException();
        }
    }

    public void changePassword(String uid, String oldPassword, String newPassword) {
        User user = userRepository.findById(uid);
        if (user == null) {
            throw new UserNotFoundException();
        }
        if (oldPassword == null || !oldPassword.equals(user.getPassword())) {
            throw new IdPwNotMatchException();
        }
        user.setPassword(newPassword);
    }
}
