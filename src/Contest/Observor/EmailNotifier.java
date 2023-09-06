package Contest.Observor;

import Contest.Contest;
import Contest.Strategy.ScoreUpdatorStrategy;
import ContestUser.User.ContestUser;

import java.util.List;

public class EmailNotifier implements ContestObservor {
    @Override
    public void update(ScoreUpdatorStrategy scoreUpdatorStrategy,
                       List<ContestUser> participants, double totalScore) {
        for(ContestUser user : participants) {
            sendEmail(user.getEmail());
        }
    }

    private void sendEmail(String emailId) {
        System.out.println("Email sent to email : " + emailId);
    }
}
