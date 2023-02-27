package guide.goodcode;


import lombok.Data;

@Data
public class Survey {
    private int id;

    public  Survey(int id) {
        this.id = id;
    }

}
