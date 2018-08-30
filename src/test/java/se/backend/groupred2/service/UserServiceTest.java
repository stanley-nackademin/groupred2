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
import java.util.Optional;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotEquals;
import static org.junit.Assert.assertThat;

@RunWith(SpringRunner.class)
@SpringBootTest
public class UserServiceTest {

    @Autowired
    UserService userService;

    @Autowired
    TeamRepository teamRepository;

    @Autowired
    UserRepository userRepository;

    Team team = new Team("nameTeam", true, 10);
    ArrayList<User> users = new ArrayList<>();
    User testUser;
    @Before
    public void setUp() {
        teamRepository.save(team);

        for (int i = 0; i < 5; i++){
            users.add(new User("activeUser"+i, "firstName"+i, "lastName"+i, true, 1000L+i));
        }
        for (User s: users) {
            s.setTeam(team);
            userRepository.save(s);
        }

        testUser = userRepository.findById(users.get(0).getId()).get();
    }


    @Test
    public void getAllUserByteamIdTest() throws Exception{
        List<User> usersFromRepo = userService.getAllUserByteamId(teamRepository.findByName("nameTeam").get().getId());
        assertEquals(usersFromRepo.get(1).toString(), users.get(1).toString());
        assertEquals(usersFromRepo.get(2).toString(), users.get(2).toString());
        assertEquals(usersFromRepo.get(3).toString(), users.get(3).toString());
        assertNotEquals(usersFromRepo.get(2).toString(), users.get(3).toString());
        assertNotEquals(usersFromRepo.get(1).toString(), users.get(4).toString());
    }

    @Test
    public void updateUserWithValidInputTest(){
        User updatedUser = new User("fname", "lname", "someusername", false, testUser.getUserNumber());

        userService.update(testUser.getId(), updatedUser);

        User checkUser = userRepository.findById(testUser.getId()).get();

        assertEquals(checkUser.getFirstName(), updatedUser.getFirstName());
        assertNotEquals(checkUser.getFirstName(), testUser.getFirstName());

        assertEquals(checkUser.getLastName(), updatedUser.getLastName());
        assertNotEquals(checkUser.getLastName(), testUser.getLastName());

        assertEquals(checkUser.getUserName(), updatedUser.getUserName());
        assertNotEquals(checkUser.getUserName(), testUser.getUserName());

        assertEquals(checkUser.isActive(), updatedUser.isActive());
        assertNotEquals(checkUser.isActive(), testUser.isActive());
    }

    @Test (expected = InvalidUserException.class)
    public void updateUserWithInvalidInputTest(){
        User updatedUser = new User("fn", "ln", "uname", false, testUser.getUserNumber());

        userService.update(testUser.getId(), updatedUser);
    }

    @After
    public void tearDown(){
        for (User s: users) {
            userRepository.delete(s);
        }
        teamRepository.delete(team);
    }

}
