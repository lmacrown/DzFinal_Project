package guide.goodcode;

import lombok.Builder;
import lombok.Data;

import java.util.List;

@Data
@Builder
public class SurveyAnswer {

    private int id;
    private Long respondentId;
    private List<Integer> answers;

}
