package Contest.Strategy;

public class EasyContestScoreUpdatorStrategy implements ScoreUpdatorStrategy {
    public int getScoreConstant() {
        return 50;
    }

    public int getNumberOfQuestions() {
        return 4;
    }
}
