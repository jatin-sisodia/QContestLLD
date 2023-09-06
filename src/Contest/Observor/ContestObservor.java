package Contest.Observor;

import Contest.Contest;
import Contest.Strategy.ScoreUpdatorStrategy;
import ContestUser.User.ContestUser;

import java.util.List;

public interface ContestObservor {
    public void update(ScoreUpdatorStrategy scoreUpdatorStrategy,
                       List<ContestUser> participants, double totalScore);
}
