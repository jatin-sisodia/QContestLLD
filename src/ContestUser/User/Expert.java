package ContestUser.User;

import ContestQuestions.QuestionDifficulty;
import ContestUser.Designation;

import static java.lang.Boolean.FALSE;
import static java.lang.Boolean.TRUE;

public class Expert extends ContestUser {
    public Expert(String username, String email){
        super(username, Designation.RED, email);
    }

    //This will validate if a customer can create a contest of certain difficulty of not
    public Boolean validateContestCreationAuthorization(QuestionDifficulty QuestionDifficulty) {
        return (QuestionDifficulty.equals(QuestionDifficulty.HARD)) ? FALSE : TRUE;
    }
}
