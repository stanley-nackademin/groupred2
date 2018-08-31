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
    private UserRepository userRepository;

    @Autowired
    private TeamRepository teamRepository;

    private Team fullTeam;
    private ArrayList<User> users = new ArrayList<>();
    List<User> usersFromDb;

    @Before
    public void setUp() {
        fullTeam = new Team("fullTeam", true, 10);
        fullTeam.setId(1L);
        teamRepository.save(fullTeam);

        for (int i = 0; i < 10; i++){
            users.add(new User("user"+i, "firstName"+i, "lastName"+i, true, 1000L+i));
        }
        for (User s: users) {
            userRepository.save(s);
        }

        usersFromDb = userRepository.findAll();
        for (User s: usersFromDb) {
            s.addTeam(fullTeam);
            userRepository.save(s);
        }
    }

    /*
     * PASSES if InvalidTeamException is thrown
     * which happens when there are more than 9 users in the requested team.*/
    @Test(expected = InvalidTeamException.class)
    public void maxUserLimitValidationTest() {
        Optional<Team> result = teamRepository.findByName("fullTeam");
        Team team = result.get();
        teamService.validateFullTeam(team);
    }

    @After
    public void tearDown(){
        for (User s: users) {
            userRepository.delete(s);
        }
        teamRepository.delete(fullTeam);
    }
}