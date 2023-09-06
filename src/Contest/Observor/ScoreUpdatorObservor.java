package Contest.Observor;

import Contest.Strategy.ScoreUpdatorStrategy;
import ContestUser.User.ContestUser;
import ContestUser.UserController;

import java.util.List;

public class ScoreUpdatorObservor implements ContestObservor {
    private UserController userController = UserController.getInstance();
    @Override
    public void update(ScoreUpdatorStrategy scoreUpdatorStrategy,
                       List<ContestUser> participants, double totalScore) {
        int scoreConstant = scoreUpdatorStrategy.getScoreConstant();
        System.out.println("Score Constant for this contest is :" + scoreConstant);
        for(ContestUser user : participants) {
            System.out.println("Updating Score for user :" + user);
            userController.updateUser(user, totalScore -scoreConstant);
        }
    }
}
