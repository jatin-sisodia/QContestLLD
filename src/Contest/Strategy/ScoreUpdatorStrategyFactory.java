package Contest.Strategy;

import ContestQuestions.QuestionDifficulty;

public class ScoreUpdatorStrategyFactory {
    public static ScoreUpdatorStrategy getObject(QuestionDifficulty difficulty) {
        switch (difficulty) {
            case HARD:
                return new HardContestScoreUpdatorStrategy();
            case MEDIUM:
                return new MediumContestScoreUpdatorStrategy();
            default:
                return new EasyContestScoreUpdatorStrategy();
        }
    }
}
