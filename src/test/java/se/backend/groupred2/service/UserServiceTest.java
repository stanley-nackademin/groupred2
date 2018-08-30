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
import se.backend.groupred2.service.exceptions.InvalidUserException;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.junit.Assert.*;

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
    User activatedUser;
    User deActivatedUser;

    User testUser;
    User jonasUser;
    @Before
    public void setUp() {
        teamRepository.save(team);
        jonasUser = new User("firstName", "lastName", "atleastTenCharacters", true, 1337L);
        userRepository.save(jonasUser);

        for (int i = 0; i < 5; i++){
            users.add(new User("activeUser"+i, "firstName"+i, "lastName"+i, true, 1000L+i));
        }
        for (User s: users) {
            s.setTeam(team);
            userRepository.save(s);
        }

        testUser = userRepository.findById(users.get(0).getId()).get();
        activatedUser = new User("activatedUser", "activatedUser", "activatedUser", true, 1L);
        deActivatedUser = new User("deActivatedUser", "deActivatedUser", "deActivatedUser", false, 2L);
        userRepository.save(activatedUser);
        userRepository.save(deActivatedUser);
    }


    //Todo: FAIL: Nullpointer Exception - should pass
    @Test
    public void getAllUserByteamIdTest() {
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

    @Test
    public void inactivateUserTest(){
        Long activeId = userRepository.findUserByFirstName("activatedUser").get(0).getId();
        assertTrue(userService.deActivate(activeId).isPresent());
        assertFalse(userService.deActivate(9893L).isPresent());
    }

    @Test(expected = InvalidTeamException.class)
    public  void inactivateUserThatIsAlreadyInactiveTest(){
        User user = userRepository.findUserByFirstName("deActivatedUser").get(0);
        userService.checkIfActive(user);

    }

    //jonas
    @Test
    public void updateUserTest(){
        User toUpdateUser = userService.getUserByUserNamefirstNameLastName(1337L, null, null, null).get(0);
        String previousFirstName = toUpdateUser.getFirstName();
        toUpdateUser.setFirstName("newName");
        assertTrue(userService.update(toUpdateUser.getId(), toUpdateUser).isPresent());
        assertFalse(userService.update(0L, toUpdateUser).isPresent());

        assertNotEquals(previousFirstName, userService.getUserByUserNamefirstNameLastName(1337L, null, null, null).get(0).getFirstName());
        assertEquals("newName", userService.getUserByUserNamefirstNameLastName(1337L, null, null, null).get(0).getFirstName());




    }

    @After
    public void tearDown(){
        for (User s: users) {
            userRepository.delete(s);
        }
        teamRepository.delete(team);
        userRepository.delete(jonasUser);

        userRepository.delete(activatedUser);
        userRepository.delete(deActivatedUser);
    }

}

