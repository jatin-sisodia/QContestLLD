package Contest.Strategy;

public class MediumContestScoreUpdatorStrategy implements ScoreUpdatorStrategy {
    public int getScoreConstant() {
        return 30;
    }

    public int getNumberOfQuestions() {
        return 3;
    }
}