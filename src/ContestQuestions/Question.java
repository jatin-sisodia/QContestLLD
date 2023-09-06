package ContestQuestions;

public class Question {
    public static int questionIdCounter = 1;
    private int questionId;
    private String name;
    private int score;
    private QuestionDifficulty difficulty;
    public Question(String name, QuestionDifficulty questionDifficulty, int score) {
        this.difficulty = questionDifficulty;
        this.name = name;
        this.score = score;
        questionId = questionIdCounter++;
    }

    public int getScore() {return score;};

    public QuestionDifficulty getDifficulty() {
        return difficulty;
    }

    @Override
    public String toString() {
        return "Question{" + "name='" + this.name
                + '\'' + ", score='" + this.score + '\'' + ", difficulty=" + this.difficulty + '}';
    }

    //BuilderDesign Pattern
    public static class QuestionBuilder {
        private String name;
        private int score;
        private QuestionDifficulty difficulty;

        public QuestionBuilder setName(String name) {
            this.name = name;
            return this;
        }

        public QuestionBuilder setDifficulty(QuestionDifficulty questionDifficulty) {
            this.difficulty = questionDifficulty;
            return this;
        }

        public QuestionBuilder setScore(int score) {
            this.score = score;
            return this;
        }

        public Question build() {
            return new Question(this.name, this.difficulty, this.score);
        }
    }
}
