package tdd.mock.contacts;

import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.when;

/*
@InjectMocks으로 Mock 객체 주입
앞의 예제는 Mock 객체를 Contacts2에 주입(Injection)하기 위해 Contacts(ContactsDatabase db)라는 생성자를 만들었습니다.
@InjectMocks을 사용하면 생성자를 만들지 않고도 Mock 객체를 주입할 수 있습니다.
먼저 다음과 같이 Contacts2(ContactsDatabase db) 생성자를 삭제합니다.
*/
public class ContactsTest2 {
    @Mock
    ContactsDatabase db;

    @InjectMocks
    Contacts2 contacts;

    @Test
    public void injectingMock() {
        MockitoAnnotations.openMocks(this);

        when(db.getName(0)).thenReturn("JS");
        when(db.getPhoneNumber(0)).thenReturn("100-1234-5678");

        assertEquals("{ JS, 100-1234-5678 }", contacts.getUserInfo(0));
    }
}
