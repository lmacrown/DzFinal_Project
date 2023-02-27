package tdd.mock.contacts;

public class Contacts {
    private ContactsDatabase db;

    public Contacts() {
        db = new ContactsDatabase();
    }

    public Contacts(ContactsDatabase db) {
        this.db = db;
    }

    public String getUserInfo(int id) {
        String userInfo= "{ " + db.getName(id) + ", "
                + db.getPhoneNumber(id) + " }";
        return userInfo;
    }

    public void addUserInfo(String name, String number) {
        db.addUserInfo(name, number);
    }
}
