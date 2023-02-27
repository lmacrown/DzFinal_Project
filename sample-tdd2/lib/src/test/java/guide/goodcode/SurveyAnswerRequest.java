package guide.goodcode;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Arrays;
import java.util.List;


@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class SurveyAnswerRequest {

    private int surveyId;
    private Long respondentId;
    private List<Integer> answers;

}
