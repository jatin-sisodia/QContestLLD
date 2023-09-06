package ContestUser.User;

import ContestQuestions.QuestionDifficulty;
import ContestUser.Designation;

import static java.lang.Boolean.TRUE;

public class Master extends ContestUser {
    public Master(String username, String email){
        super(username, Designation.BLACK, email);
    }

    //This will validate if a customer can create a contest of certain difficulty of not
    public Boolean validateContestCreationAuthorization(QuestionDifficulty QuestionDifficulty) {
        return TRUE;
    }
}
