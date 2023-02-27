package guide.goodcode.user;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class User {
    private String id;
    private String name;
    private String password;
    private String email;
    private Address address;

    public User(String id, String password, String email) {
        this.id = id;
        this.password = password;
        this.email = email;
    }

    public User(String id, String password, Address address) {
        this.id = id;
        this.password = password;
        this.email = "";
        this.address = address;
    }

    //비밀번호가 맞는지 확인
    public boolean matchPassword(String password) {
        return this.password != null && this.password.equals(password);
    }
}
