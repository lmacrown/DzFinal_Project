package guide.goodcode.user;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class Address {
    private String city;
    private String detail;

    public Address(String city, String detail) {
        this.city = city;
        this.detail = detail;
    }

    public String getCity() {
        return city;
    }

    public String getDetail() {
        return detail;
    }
}
