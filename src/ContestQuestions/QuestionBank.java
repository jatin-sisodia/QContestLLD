package ContestQuestions;

import ContestUser.User.ContestUser;
import ContestUser.UserController;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

//Singleton Design Pattern
//Class to Manage all set of Questions
public class QuestionBank {
    private static QuestionBank questionBank;
    private List<Question> questions = new ArrayList<>();

    private QuestionBank () {};

    public static QuestionBank getInstance() {
        if(questionBank == null) {
            synchronized(QuestionBank.class) {
                if(questionBank == null) {
                    questionBank = new QuestionBank();
                }
            }
        }

        return questionBank;
    }

    public void setQuestionsList(List<Question> questions) {
        this.questions = questions;
    }

    public List<Question> listQuestions(QuestionDifficulty questionDifficulty) {
        if(questionDifficulty == null) {
            return questions;
        }
        List<Question> questionList = questions.stream()
                .filter(question -> question.getDifficulty() == questionDifficulty)
                .collect(Collectors.toList());

        if(questionList.isEmpty()) {
            return questions;
        }
        return questionList;
    }

    public Question createQuestion(String title, QuestionDifficulty questionDifficulty, int score) {
        Question newQuestion =  new Question.QuestionBuilder()
                                .setName(title)
                                .setDifficulty(questionDifficulty)
                                .setScore(score)
                                .build();
        //OR Question newQuestion = new Question(title, questionDifficulty, score);
        questions.add(newQuestion);
        return newQuestion;
    }
}
