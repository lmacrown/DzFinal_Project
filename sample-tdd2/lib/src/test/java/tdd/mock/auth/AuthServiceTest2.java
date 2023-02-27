package tdd.mock.auth;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.mockito.Spy;

import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;
import static org.mockito.Mockito.times;

public class AuthServiceTest2 {
    @Mock
    AuthDAO authDAO;

    @InjectMocks
    AuthService service;

    @BeforeEach
    void SeuUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void mockObjectTest() {
        //@BeforeEach 어너테이션에 정의한 메서드에 호출하게 설정함 아래 코드 필요 없음
        //MockitoAnnotations.openMocks(this);

        assertTrue(service != null);
        assertTrue(service.getAuthDAO() != null);
    }

    @Test
    void 로그인여부확인테스트() {
        //@BeforeEach 어너테이션에 정의한 메서드에 호출하게 설정함 아래 코드 필요 없음
        //MockitoAnnotations.openMocks(this);

        when(authDAO.isLogin(eq("hong"))).thenReturn(true);

        assertTrue(service.isLogin("hong") == true);
        assertTrue(service.isLogin("lee") == false);
    }



}
