package mock.mock;

import mock.fake.User;
import mock.fake.UserRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import static org.assertj.core.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class UserServiceTest {
    @Mock
    private UserRepository userRepository;

    @Test
    void test() {
        when(userRepository.findById(anyLong())).thenReturn(new User(1L, "Test User"));

        User actual = userRepository.findById(1);
        assertThat(actual.getId()).isEqualTo(1);
        assertThat(actual.getName()).isEqualTo("Test User");
    }
}