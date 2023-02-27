package tdd.mock.contacts;

import java.util.ArrayList;
import java.util.List;

public class ContactsDatabase {
    List<String> names = new ArrayList<>();
    List<String> numbers = new ArrayList<>();

    public ContactsDatabase() {
        names.add("AAA");
        numbers.add("000-1234");
    }

    public String getName(int id) {
        return names.get(id);
    }

    public String getPhoneNumber(int id) {
        return numbers.get(id);
    }

    public void addUserInfo(String name, String number) {
        names.add(name);
        numbers.add(number);
    }
}
