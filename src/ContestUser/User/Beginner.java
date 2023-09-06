package ContestUser.User;

import static java.lang.Boolean.FALSE;
import static java.lang.Boolean.TRUE;

import ContestQuestions.QuestionDifficulty;
import ContestUser.Designation;

public class Beginner extends ContestUser {
    public Beginner(String username, String email){
        super(username, Designation.RED, email);
    }

    //This will validate if a customer can create a contest of certain difficulty of not
    public Boolean validateContestCreationAuthorization(QuestionDifficulty QuestionDifficulty) {
        return (QuestionDifficulty.equals(QuestionDifficulty.EASY)) ? TRUE : FALSE;
    }
}
