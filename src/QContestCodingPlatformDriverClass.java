import Contest.Contest;
import Contest.ContestController;
import ContestQuestions.Question;
import ContestQuestions.QuestionBank;
import ContestQuestions.QuestionDifficulty;
import ContestUser.User.Beginner;
import ContestUser.User.ContestUser;
import ContestUser.UserController;

import java.math.BigInteger;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;

public class QContestCodingPlatformDriverClass {
    public ContestController contestController = ContestController.getInstance();
    public UserController userController = UserController.getInstance();
    public QuestionBank questionBank = QuestionBank.getInstance();

    public void drivePlatform() {
        //Step 1 : Creating Users
        ContestUser JatinCoder = userController.createUser("Jatin", "jatin123@gmail.com");
        ContestUser ManishCoder = userController.createUser("GoluMeraMonuMonkey", "MonaMonkey123@gmail.com");
        ContestUser HarshCoder = userController.createUser("Harsh", "Harsh123@gmail.com");

        List<ContestUser> users = userController.getUsers();
        System.out.println("All users are as follows: " + users);

        //Step 2 : Create Questions
        Question question1 = questionBank.createQuestion("easyQ1", QuestionDifficulty.EASY, 10);
        Question question2 = questionBank.createQuestion("easyQ2", QuestionDifficulty.EASY, 15);
        Question question3 = questionBank.createQuestion("medQ1", QuestionDifficulty.MEDIUM, 20);
        Question question4 = questionBank.createQuestion("medQ1", QuestionDifficulty.MEDIUM, 25);
        Question question5 = questionBank.createQuestion("hardQ1", QuestionDifficulty.HARD, 30);
        Question question6 = questionBank.createQuestion("hardQ1", QuestionDifficulty.HARD, 35);

        System.out.println("Low Level Questions" + questionBank.listQuestions(QuestionDifficulty.EASY));
        System.out.println("Medium Level Questions" + questionBank.listQuestions(QuestionDifficulty.MEDIUM));
        System.out.println("Hard Level Questions" + questionBank.listQuestions(QuestionDifficulty.HARD));

        //Step 3 : Create Contest
        Contest contest1 = contestController
                .createContest("Contest1", JatinCoder, QuestionDifficulty.EASY);
        Contest contest2 = contestController
                .createContest("Contest2", ManishCoder, QuestionDifficulty.MEDIUM);

        System.out.println("Easy Contests are :" + contestController.getListOfContests(QuestionDifficulty.EASY));
        System.out.println("Medium Contests are :" + contestController.getListOfContests(QuestionDifficulty.MEDIUM));
        System.out.println("Hard Contests are :" + contestController.getListOfContests(QuestionDifficulty.HARD));

        //Step 4 : user can register contest
        HarshCoder.attendContest(contest1.getId());
        HarshCoder.attendContest(contest2.getId());
        JatinCoder.attendContest(contest2.getId());

        //Valid User running contest
        ManishCoder.runContest(contest2.getId());

        //Valid User running contest & contest 2 already ended
//        JatinCoder.runContest(contest2.getId());

        //InValid User running contest
//        HarshCoder.runContest(contest1.getId());

        //Step 5 : leader board
        final List<ContestUser> leaderBoard = userController.fetchLeaderboard("DESC");
        System.out.println("leader board");
        leaderBoard.forEach(System.out::println);

        //Step 6 : contest history
        System.out.println("\ncheck history");

        //Invalid as contest 1 has not started
//        contestController.contestHistory(contest1.getId()).forEach(System.out::println);
        contestController.contestHistory(contest2.getId()).forEach(System.out::println);

        //Step 7 : withdraw contest
        //Invlaid Withdrawl
//        ManishCoder.withdrawContest(contest2.getId());
        //Valid Withdrawl
        ManishCoder.withdrawContest(contest1.getId());
    }
}
