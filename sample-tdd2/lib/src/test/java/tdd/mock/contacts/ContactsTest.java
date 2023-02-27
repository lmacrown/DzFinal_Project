package tdd.mock.contacts;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

//MockitoAnnotations.openMocks(this); 대신 MockitoExtension.class를 사용해도 됨
//@ExtendWith(MockitoExtension.class)
public class ContactsTest {
    @Mock
    ContactsDatabase db;

    @Test
    public void injectingMock() {
        MockitoAnnotations.openMocks(this);

        Contacts contacts = new Contacts(db);
        when(db.getName(0)).thenReturn("JS");
        when(db.getPhoneNumber(0)).thenReturn("100-1234-5678");

        assertEquals("{ JS, 100-1234-5678 }", contacts.getUserInfo(0));
    }
}
