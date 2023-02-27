package guide.goodcode;

import java.util.HashMap;
import java.util.Map;

public class MemoryRepository {
    Map<String, SurveyAnswer> surveyAnswerMap = new HashMap<>();

    private String getKey(int id, Long respondentId) {
        return String.valueOf(id) + "_" + String.valueOf(respondentId);
    }

    public SurveyAnswer findBySurveyAndRespondent(int id, Long respondentId) {
        return surveyAnswerMap.get(String.valueOf(id) + "_" + String.valueOf(respondentId));
    }

    public void answerSurvey(SurveyAnswerRequest surveyAnswerRequest) {
        SurveyAnswer surveyAnswer = SurveyAnswer.builder()
                .id(surveyAnswerRequest.getSurveyId())
                .respondentId(surveyAnswerRequest.getRespondentId())
                .answers(surveyAnswerRequest.getAnswers())
                .build();

        surveyAnswerMap.put(getKey(surveyAnswerRequest.getSurveyId(), surveyAnswerRequest.getRespondentId()), surveyAnswer);
    }
}
