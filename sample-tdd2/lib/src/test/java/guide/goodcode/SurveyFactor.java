package guide.goodcode;

public class SurveyFactor {
    public static Survey createApprovedSurvey(int surveyId) {
        return new Survey(surveyId);
    }
}
