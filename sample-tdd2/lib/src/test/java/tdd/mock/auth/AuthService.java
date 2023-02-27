package tdd.mock.auth;

public class AuthService {
    private AuthDAO authDAO;

    public void setAuthDAO(AuthDAO authDAO) {
        this.authDAO = authDAO;
    }

    public AuthDAO getAuthDAO() {
        return authDAO;
    }

    public boolean isLogin(String userId) {
        return authDAO.isLogin(userId);
    }
}
