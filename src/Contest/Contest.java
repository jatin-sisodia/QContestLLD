package Contest;

import Contest.Enums.ContestStatus;
import Contest.Observor.ContestObservor;
import Contest.Strategy.ScoreUpdatorStrategy;
import Contest.Strategy.ScoreUpdatorStrategyFactory;
import ContestQuestions.Question;
import ContestQuestions.QuestionBank;
import ContestQuestions.QuestionDifficulty;
import ContestUser.User.ContestUser;

import java.util.*;

import static java.lang.Boolean.FALSE;
import static java.lang.Boolean.TRUE;

public class Contest {
    public static int contestIdCounter = 1;
    private List<Question> contestQuestions;
    private List<ContestUser> participants;
    private Integer contestId;
    private String name;
    private QuestionDifficulty difficulty;
    private ContestStatus contestStatus;
    //Observor Design Pattern
    private List<ContestObservor> contestObservorList;
    //Strategy Design Pattern
    private ScoreUpdatorStrategy scoreUpdatorStrategy;
    private QuestionBank questionBank;
    private ContestUser createdBy;

    public Contest(String name, QuestionDifficulty difficulty, ContestUser createdBy) {
        System.out.println("Creating new Contest with name :" + name);
        contestObservorList = new ArrayList<>();
        participants = new ArrayList<>();
        //Factory Design Pattern
        scoreUpdatorStrategy = ScoreUpdatorStrategyFactory.getObject(difficulty);
        questionBank = QuestionBank.getInstance();
        this.name = name;
        this.contestId = contestIdCounter++;
        this.difficulty = difficulty;
        this.contestStatus = ContestStatus.CREATED;
        this.createdBy = createdBy;
        
        populateContestQuestions();
    }

    private List<Question> populateContestQuestions() {
        int numberOfQuestionsAsPerDifficulty = scoreUpdatorStrategy.getNumberOfQuestions();
        List<Question> questionList = questionBank.listQuestions(difficulty);

        if(numberOfQuestionsAsPerDifficulty >  questionList.size()) {
            questionList.addAll(questionBank.listQuestions(null));
        }

        this.contestQuestions = questionList;
        return questionList;
    }

    public void addObservor(ContestObservor contestObservor) {
        contestObservorList.add(contestObservor);
    }

    public List<ContestUser> runContest(ContestUser runBy) {
        if(this.getCreatedBy().getUserId() != runBy.getUserId()) {
            System.out.println("Given Contestant cannot run the contest as [s]he is not the creator");
            throw new RuntimeException("Given Contestant canot run the contest as [s]he is not the creator");
        }

        contestStatus = ContestStatus.INPROGRESS;
        System.out.println("Contest Started");

        double totalScore = 0;
        for(Question question : contestQuestions) {
            totalScore += question.getScore();
        }
        for(ContestObservor contestObservor: contestObservorList) {
            contestObservor.update(scoreUpdatorStrategy, participants, totalScore);
        }

        contestStatus = ContestStatus.COMPLETED;
        System.out.println("Contest Ended");

        Collections.sort(this.participants, new Comparator<ContestUser>() {
            @Override
            public int compare(ContestUser user1, ContestUser user2) {
                return (int)(user1.getScore() - user2.getScore());
            }
        });

        return this.participants;
    }

    public Boolean attendContest(ContestUser user) {
        this.participants.add(user);
        System.out.println("Participant :" + user + " has been successfully registered to the contest");
        return TRUE;
    }

    public Boolean withdrawContest(ContestUser user) {
        Optional<ContestUser> userOptional = this.participants.stream()
                .filter(u -> u.getUserId() == user.getUserId())
                .findFirst();
        if(!userOptional.isPresent()) {
            System.out.println("Given Contestant is not registered in the contest");
            throw new RuntimeException("Given Contestant is not registered in the contest");
        }

        return participants.remove(user);
    }

    public QuestionDifficulty getDifficulty() {
        return this.difficulty;
    }

    public ContestUser addParticipant(ContestUser contestUser) {
        participants.add(contestUser);
        return contestUser;
    }

    public Integer getId() {
        return this.contestId;
    }

    public ContestUser getCreatedBy() {
        return this.createdBy;
    }

    public ContestStatus getContestStatus() {
        return this.contestStatus;
    }

    public List<ContestUser> getParticipants() {
        return participants;
    }

    @Override
    public String toString() {
        return "Contest{" + "name='" + this.name
                + '\'' + ", contest Status='" + this.contestStatus
                + '\'' + ", difficulty=" + this.difficulty
                + '\'' + ", participants='" + this.participants
                + '\'' + ", contestQuestions='" + this.contestQuestions
                + '}';
    }
}
