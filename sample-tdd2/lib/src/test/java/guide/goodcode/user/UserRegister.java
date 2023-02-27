package guide.goodcode.user;

public class UserRegister {
    private WeakPasswordChecker passwordChecker;
    private UserRepository userRepository;
    private EmailNotifier emailNotifier;

    public UserRegister(WeakPasswordChecker passwordChecker,
                        UserRepository userRepository,
                        EmailNotifier emailNotifier) {
        this.passwordChecker = passwordChecker;
        this.userRepository = userRepository;
        this.emailNotifier = emailNotifier;
    }

    public void register(String id, String pw, String email) {
        if (passwordChecker.checkPasswordWeak(pw)) {
            throw new WeakPasswordException();
        }
        User user = userRepository.findById(id);
        if (user != null) {
            throw new DupIdException();
        }
        userRepository.save(new User(id, pw, email));

        emailNotifier.sendRegisterEmail(email);
    }

    //약한 암호 검사 루틴 보다 먼저 findById() 호출하여 아이디 존재 여부를 확인하게 루틴 수정
    public void register2(String id, String pw, String email) {
        User user = userRepository.findById(id);
        if (user != null) {
            throw new DupIdException();
        }
        if (passwordChecker.checkPasswordWeak(pw)) {
            throw new WeakPasswordException();
        }
        userRepository.save(new User(id, pw, email));

        emailNotifier.sendRegisterEmail(email);
    }
}
