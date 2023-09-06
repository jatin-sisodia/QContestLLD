package ContestQuestions;

public enum QuestionDifficulty {
    EASY(100),
    MEDIUM(200),
    HARD(300);

    private int score;
    private QuestionDifficulty(int score) {
        this.score = score;
    }
    public int getScore() {
        return score;
    }
}
