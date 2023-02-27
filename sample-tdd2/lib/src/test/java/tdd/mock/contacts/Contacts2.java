package tdd.mock.contacts;

public class Contacts2 {
    private ContactsDatabase db;

    public Contacts2() {
        db = new ContactsDatabase();
    }
    
    //생성자를 이용하여 ContactsDatabase 객체 주입하는 부분 삭제
//    public Contacts2(ContactsDatabase db) {
//        this.db = db;
//    }

    public String getUserInfo(int id) {
        String userInfo= "{ " + db.getName(id) + ", "
                + db.getPhoneNumber(id) + " }";
        return userInfo;
    }

    public void addUserInfo(String name, String number) {
        db.addUserInfo(name, number);
    }
}
