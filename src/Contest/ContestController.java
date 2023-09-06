package Contest;

import Contest.Enums.ContestStatus;
import ContestQuestions.QuestionDifficulty;
import ContestUser.User.ContestUser;

import java.util.*;
import java.util.stream.Collectors;

import static java.lang.Boolean.FALSE;
import static java.lang.Boolean.TRUE;

//Singleton Class Design Pattern to Manage Contests and Expose Contests Wide APIs
//Or use @Singleton annotation in Spring
public class ContestController {
    private static ContestController contestController;
    Map<Integer, ContestStatus> statusVsContestMap = new HashMap<>();
    List<Contest> allContests = new ArrayList<>();

    private ContestController () {};

    public static ContestController getInstance() {
        if(contestController == null) {
            synchronized(ContestController.class) {
                if(contestController == null) {
                    contestController = new ContestController();
                }
            }
        }

        return contestController;
    }
    public void setContestsList(List<Contest> contests) {
        System.out.println("Setting contests");
        this.allContests = contests;
        for(Contest contest : contests) {
            System.out.println("Setting contest status for contest with id" + contest.getId());
            statusVsContestMap.put(contest.getId(), ContestStatus.CREATED);
        }
    }

    public List<Contest> getListOfContests(QuestionDifficulty difficulty) {
        if (difficulty == null) {
            System.out.println("Returning all contests since no input of difficulty is given");
            return allContests;
        }
        List<Contest> contestList = allContests.stream()
                .filter(contest -> contest.getDifficulty() == difficulty)
                .collect(Collectors.toList());
        if (contestList.isEmpty()) {
            System.out.println("Returning all contests since no input of difficulty is given");
            return allContests;
        }
        return contestList;
    }
    public Contest createContest(String contestName, ContestUser createdBy, QuestionDifficulty difficulty) {
        Contest newContest = new Contest(contestName, difficulty, createdBy);
        newContest.addParticipant(createdBy);
        this.allContests.add(newContest);
        this.statusVsContestMap.put(newContest.getId(), ContestStatus.CREATED);
        return newContest;
    }

    public List<ContestUser> runContest(Integer contestId, ContestUser runBy) {
        Optional<Contest> contest = getContestFromContestId(contestId);
        contestRuntimeExceptionValidation(contest.get());
        return contest.get().runContest(runBy);
    }

    public Boolean attendContest(Integer contestId, ContestUser user) {
        Optional<Contest> contestOptional = getContestFromContestId(contestId);
        contestRuntimeExceptionValidation(contestOptional.get());
        return contestOptional.get().attendContest(user);
    }

    public Boolean withdrawContest(Integer contestId, ContestUser user){
        Optional<Contest> contestOptional = getContestFromContestId(contestId);

        if(contestOptional.get().getCreatedBy().getUserId() == user.getUserId()) {
            System.out.println("Contestant who created cannot withdraw from contest");
            throw new RuntimeException("Contestant who created cannot withdraw from contest");
        }
        contestRuntimeExceptionValidation(contestOptional.get());
        return contestOptional.get().withdrawContest(user);
    }

    public List<ContestUser> contestHistory(Integer contestId){
        Optional<Contest> contestOptional = getContestFromContestId(contestId);
        Contest contest = contestOptional.get();
        if (contest.getContestStatus().equals(ContestStatus.INPROGRESS) ||
                contest.getContestStatus().equals(ContestStatus.CREATED)) {
            System.out.println("Contest has not ended");
            throw new RuntimeException("Contest has not ended");
        }

        List<ContestUser> contestParticipants = contest.getParticipants();
        Collections.sort(contestParticipants, new Comparator<ContestUser>() {
            @Override
            public int compare(ContestUser user1, ContestUser user2) {
                return (int)(user1.getScore() - user2.getScore());
            }
        });

        return contestParticipants;
    }

    private Optional<Contest> getContestFromContestId(Integer contestId) {
        Optional<Contest> contestOptional = allContests.stream().
                filter(c -> c.getId().equals(contestId)).
                findFirst();
        if(!contestOptional.isPresent()) {
            System.out.println("Contest with given contestId is not present");
            throw new RuntimeException("Contest with given contestId is not present");
        }
        return contestOptional;
    }

    private void contestRuntimeExceptionValidation(Contest contest) {
        if (contest.getContestStatus().equals(ContestStatus.INPROGRESS)) {
            System.out.println("Contest has already started");
            throw new RuntimeException("Contest has already started");
        }
        if (contest.getContestStatus().equals(ContestStatus.COMPLETED)) {
            throw new RuntimeException("Contest has already ended");
        }
    }
}
