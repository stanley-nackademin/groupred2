package se.backend.groupred2.service;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;
import se.backend.groupred2.model.Team;
import se.backend.groupred2.model.User;
import se.backend.groupred2.repository.TeamRepository;
import se.backend.groupred2.repository.UserRepository;
import se.backend.groupred2.service.exceptions.InvalidTeamException;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@RunWith(SpringRunner.class)
@SpringBootTest
public class TeamServiceTest {

    @Autowired
    private TeamService teamService;

    @Autowired
    private UserService userService;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private TeamRepository teamRepository;

    private ArrayList<User> usersForMaxUserLimitValidationTest = new ArrayList<>();

    ArrayList<User> users = new ArrayList<>();

    @Before
    public void setUp() {
        //team for maxUserLimitValidationTest
        teamService.createTeam(new Team("fullTeam", true, 10));
        Team fullTeam = teamRepository.findByName("fullTeam").get();

        //list of users for maxUserLimitValidationTest
        for (int i = 0; i < 10; i++) {
            usersForMaxUserLimitValidationTest.add(new User("usersForMaxUserLimitValidation", "firstName" + i, "username00" + i, true, 1000L + i));
        }

        for (User s : usersForMaxUserLimitValidationTest) {
            userService.createUser(s);
        }

        List<User> usersWithIdFromDatabase = userRepository.findUserByFirstName("usersForMaxUserLimitValidation");
        for (User s : usersWithIdFromDatabase) {
            teamService.addUser(fullTeam.getId(), s.getId());
        }

        User extraUserForMaxUserLimitValidationTest = new User("extraUser", "extraUser", "extraUser123", true, 2001L);
        userService.createUser(extraUserForMaxUserLimitValidationTest);

        //Teams for validateUserTeamLimitTest and validateUserAlreadyPartOfTeamTest
        teamService.createTeam(new Team("team1", true, 10));
        teamService.createTeam(new Team("team2", true, 10));
        teamService.createTeam(new Team("team3", true, 10));

        //The only way to get correct teamId is to get the teams from the database since they are now generated when created.
        Team team1 = teamRepository.findByName("team1").get();
        Team team2 = teamRepository.findByName("team2").get();
        Team team3 = teamRepository.findByName("team3").get();

        userService.createUser(new User("userWithTooManyTeams", "userWithTooManyTeams", "userWithTooManyTeams", true, 151L));
        User userWithTooManyTeams = userRepository.findUserByUserName("userWithTooManyTeams").get(0);
        teamService.addUser(team1.getId(), userWithTooManyTeams.getId());
        teamService.addUser(team2.getId(), userWithTooManyTeams.getId());
        teamService.addUser(team3.getId(), userWithTooManyTeams.getId());

        //users for validateUserAlreadyPartOfTeamTest
        userRepository.save(new User("userAlreadyPartOfATeam", "test", "userAlreadyPartOfATeam", true, 152L));
        User userAlreadyPartOfATeamTest = userRepository.findUserByUserName("userAlreadyPartOfATeam").get(0);
        teamService.addUser(team1.getId(), userAlreadyPartOfATeamTest.getId());
    }

    /*
     * PASSES if InvalidTeamException is thrown
     * which happens when there are more than 9 users in the requested team.*/
    @Test(expected = InvalidTeamException.class)
    public void maxUserLimitValidationTest() {
        Team fullTeam = teamRepository.findByName("fullTeam").get();
        User extraUser = userRepository.findUserByUserName("extraUser123").get(0);
        teamService.addUser(fullTeam.getId(), extraUser.getId());
    }

    /*
     * PASSES if InvalidTeamException is thrown
     * which happens when a user has more than 2 teams.*/
    @Test(expected = InvalidTeamException.class)
    public void validateUserTeamLimitTest() {
        User userWithTooManyTeams = userRepository.findUserByUserName("userWithTooManyTeams").get(0);
        teamService.validateUserTeamLimit(userWithTooManyTeams);
    }

    /*
     * PASSES if InvalidTeamException is thrown
     * which happens when the user is already a part of the team.*/
    @Test(expected = InvalidTeamException.class)
    public void validateUserAlreadyPartOfTeamTest() {
        User userAlreadyPartOfATeamTest = userRepository.findUserByUserName("userAlreadyPartOfATeam").get(0);
        Team team1 = teamRepository.findByName("team1").get();
        teamService.validateUserAlreadyPartOfTeam(userAlreadyPartOfATeamTest, team1);
    }

    @After
    public void tearDown() {
        List<User> usersForMaxUserLimitValidation = userRepository.findUserByFirstName("usersForMaxUserLimitValidation");
        for (User s : usersForMaxUserLimitValidation) {
            userRepository.delete(s);
        }

        User userWithTooManyTeams;
        List<User> result = userRepository.findUserByUserName("userWithTooManyTeams");
        if (!result.isEmpty()) {
            userWithTooManyTeams = result.get(0);
            userRepository.delete(userWithTooManyTeams);
        }

        User userAlreadyPartOfATeamTest;
        List<User> result2 = userRepository.findUserByUserName("userAlreadyPartOfATeam");
        if (!result.isEmpty()) {
            userAlreadyPartOfATeamTest = result2.get(0);
            userRepository.delete(userAlreadyPartOfATeamTest);
        }

        User extraUser;
        List<User> result3 = userRepository.findUserByUserName("extraUser123");
        if (!result3.isEmpty()) {
            extraUser = result3.get(0);
            userRepository.delete(extraUser);
        }

        Team fullTeam;
        Optional<Team> result4 = teamRepository.findByName("fullTEam");
        if (result4.isPresent()) {
            fullTeam = result4.get();
            System.out.println("TEAM TO DELETE: " + fullTeam.toString());
            teamRepository.delete(fullTeam);
        }

        Team team1;
        Optional<Team> result5 = teamRepository.findByName("team1");
        if (result5.isPresent()) {
            team1 = result5.get();
            teamRepository.delete(team1);
        }

        Team team2;
        Optional<Team> result6 = teamRepository.findByName("team2");
        if (result6.isPresent()) {
            team2 = result6.get();
            teamRepository.delete(team2);
        }

        Team team3;
        Optional<Team> result7 = teamRepository.findByName("team3");
        if (result7.isPresent()) {
            team3 = result7.get();
            teamRepository.delete(team3);
        }
    }
}
