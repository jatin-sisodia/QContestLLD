package ContestUser.User;

import Contest.Contest;
import Contest.ContestController;

import ContestQuestions.QuestionDifficulty;
import ContestUser.Designation;

import java.util.ArrayList;
import java.util.List;

public abstract class ContestUser {
    //for auto incrementing Ids, Alternatively we can use UUID as well
    public static int userIdCounter = 1;
    private int userId;
    private String email;
    //List of contests user participated in
    private List<Contest> contests;
    private String username;
    private double score;
    private Designation designation;
    //Singleton Design Pattern for Controller classes
    private ContestController contestController;

    //Constructor Injection
    ContestUser(String username, Designation designation, String email){
        this.contestController = ContestController.getInstance();
        this.designation = designation;
        this.userId = userIdCounter++;
        //Assign Base Score
        this.score = designation.getValue();
        this.contests = new ArrayList<>();
        this.email = email;
        this.username = username;
    }

    public abstract Boolean validateContestCreationAuthorization(
            QuestionDifficulty QuestionDifficulty);

    //Returns Contest
    public Contest createContest(String contestName, QuestionDifficulty difficulty) {
        validateContestCreationAuthorization(difficulty);
        Contest contest = contestController.createContest(contestName, this, difficulty);
        this.contests.add(contest);
        return contest;
    }

    public List<ContestUser> runContest(Integer contestId) {
        return contestController.runContest(contestId, this);
    }

    public Boolean attendContest(Integer contestId) {
        return contestController.attendContest(contestId,this);
    }

    public Boolean withdrawContest(Integer contestId){
        return contestController.withdrawContest(contestId, this);
    }

    public double updateScoreAndDesignation(Integer newScore) {
        score = score + newScore;
        return score;
    }

    //Alternatively in Spring we can use Getter and Setter annotations
    public int getUserId() {
        return this.userId;
    }

    public double getScore() {
        return this.score;
    }

    public void setScore(double score) {
        this.score = score;
    }

    public String getUsername() {
        return this.username;
    }
    public String getEmail() {
        return this.email;
    }

    @Override
    public String toString() {
        return "User{" + "name='" + this.username
                + '\'' + ", email=" + this.email
                + '\'' + ", score=" + this.score
                + '}';
    }
}
