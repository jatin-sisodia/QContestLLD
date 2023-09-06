package ContestUser;

import ContestUser.User.Beginner;
import ContestUser.User.ContestUser;
import ContestUser.User.Expert;
import ContestUser.User.Master;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

//Singleton Design Pattern
//User Controller Class to Manage Users
//For performing collective or Aggregation on Users
public class UserController {

    private static UserController userController;
    //List of All Users
    List<ContestUser> users = new ArrayList<>();
    private UserController(){}

    public static UserController getInstance() {
        if(userController == null) {
            synchronized(UserController.class) {
                if(userController == null) {
                    userController = new UserController();
                }
            }
        }

        return userController;
    }
    public void setUserList(List<ContestUser> users) {
        this.users = users;
    }

    //create a new User
    public ContestUser createUser(String username, String email) {
        ContestUser contestUser = new Beginner(username, email);
        users.add(contestUser);
        return contestUser;
    }

    public List<ContestUser> fetchLeaderboard(String order) {
        Collections.sort(this.users, new Comparator<ContestUser>() {
            @Override
            public int compare(ContestUser user1, ContestUser user2) {
                int diff = (int)(user1.getScore() - user2.getScore());
                return order.equals("ASC") ? -1*diff : diff;
            }
        });
        return users;
    }

    public List<ContestUser> getUsers() {
        return users;
    }

    //Updates Score and Designation of User as per New Updated Score
    public ContestUser updateUser(ContestUser contestUser, double newScore) {
        updateUserObject(contestUser, newScore);
        return contestUser;
    }

    private void updateUserObject(ContestUser contestUser, double newScore) {
        double score = contestUser.getScore() + newScore;
        String username = contestUser.getUsername();
        String email = contestUser.getEmail();
        if(score > Designation.BLACK.getValue()) {
            contestUser = new Master(username, email);
        } else if(score > Designation.RED.getValue()) {
            contestUser = new Expert(username, email);
        } else {
            contestUser = new Beginner(username, email);
        }

        contestUser.setScore(score);
    }
}
