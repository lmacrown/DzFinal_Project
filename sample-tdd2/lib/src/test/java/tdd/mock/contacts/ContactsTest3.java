package tdd.mock.contacts;

import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.spy;
import static org.mockito.Mockito.when;

/*
Spy 객체에 Mock 객체 주입
Spy 객체에 Mock을 주입할 떄는 @InjectMocks를 사용할 수 없습니다.
객체를 생성할 때 생성자에 Mock을 인자로 전달하는 방법을 사용해야 합니다.
다음과 같이 spy()에 ContactsDatabase의 Mock 객체가 주입된 Contacts 객체를 인자로 전달하여 spy 객체를 만들 수 있습니다.
 */
public class ContactsTest3 {
    @Mock
    ContactsDatabase db;

//    @InjectMocks
//    Contacts2 contacts;

    @Test
    public void injectingMock() {
        MockitoAnnotations.openMocks(this);

        //다음과 같이 spy()에 ContactsDatabase의 Mock 객체가 주입된 Contacts 객체를 인자로 전달하여 spy 객체를 만들 수 있습니다.
        Contacts contacts = spy(new Contacts(db));

        when(db.getName(0)).thenReturn("JS");
        when(db.getPhoneNumber(0)).thenReturn("100-1234-5678");

        assertEquals("{ JS, 100-1234-5678 }", contacts.getUserInfo(0));
    }
}
