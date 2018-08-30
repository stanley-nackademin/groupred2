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
import se.backend.groupred2.service.exceptions.InvalidUserException;

import java.util.ArrayList;
import java.util.List;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotEquals;

@RunWith(SpringRunner.class)
@SpringBootTest
public class UserServiceTest {

    @Autowired
    UserService userService;

    @Autowired
    TeamRepository teamRepository;

    @Autowired
    UserRepository userRepository;

    private Team team = new Team("nameTeam", true, 10);
    private ArrayList<User> users = new ArrayList<>();

    @Before
    public void setUp() {
        teamRepository.save(team);

        for (int i = 0; i < 5; i++){
            users.add(new User("user"+i, "firstName"+i, "lastName"+i, true, 1000L+i));
        }
        for (User s: users) {
            userRepository.save(s);
        }

        List<User> usersFromDb = userRepository.findAll();
        for (User s: usersFromDb) {
            s.addTeam(team);
            userRepository.save(s);
        }
    }

    @Test
    public void getAllUserByTeamIdTest(){
        List<User> usersFromRepo = userService.getAllUserByteamId(teamRepository.findByName("nameTeam").get().getId());
        assertEquals(usersFromRepo.get(1).toString(), users.get(1).toString());
        assertEquals(usersFromRepo.get(2).toString(), users.get(2).toString());
        assertEquals(usersFromRepo.get(3).toString(), users.get(3).toString());
        assertNotEquals(usersFromRepo.get(2).toString(), users.get(3).toString());
        assertNotEquals(usersFromRepo.get(1).toString(), users.get(4).toString());
    }

    @Test(expected = InvalidUserException.class)
    public void getAllUsersByTeamIdInvalidDatatTest(){
        List<User> emptyUserList = userService.getAllUserByteamId(10000L);
    }

    @After
    public void tearDown(){
        for (User s: users) {
            userRepository.delete(s);
        }
        teamRepository.delete(team);
    }
}
