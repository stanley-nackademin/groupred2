package se.backend.groupred2.service;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;
import se.backend.groupred2.model.User;
import se.backend.groupred2.repository.TeamRepository;
import se.backend.groupred2.repository.UserRepository;
import se.backend.groupred2.service.exceptions.InvalidUserException;

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

    private User testUser;
    private User activatedUser;
    private User user = new User("myFirstname", "myLastname", "MyUsernameee", true, 112233L);
    private User badUser = new User("myFirstname", "myLastname", "tooShort", true, 11223L);

    @Before
    public void setUp() {
        userRepository.save(user);
        userRepository.save(badUser);

        activatedUser = new User("activatedUser", "activatedUser", "activatedUser", true, 1L);
        userRepository.save(activatedUser);
    }

    @Test
    public void createUserTest() {
        List<User> result = userRepository.findByUserNumber(112233L);
        assertEquals(!result.isEmpty(), !result.isEmpty());
    }

    @Test(expected = InvalidUserException.class)
    public void createUserTestBadUserNumberTest() {
        userService.validate(badUser);
    }

    @Test
    public void shouldChangeValidUsername() {
        String oldUsername = activatedUser.getUserName();
        String newUsername = "ThisIsSomeNewUsername";
        activatedUser.setUserName(newUsername);
        userService.update(activatedUser.getId(), activatedUser);

        testUser = userRepository.findById(activatedUser.getId()).get();

        assertEquals(testUser.getUserName(), newUsername);
        assertNotEquals(testUser.getUserName(), oldUsername);
    }

    @Test(expected = InvalidUserException.class)
    public void shouldNotAcceptShortUsername() {
        String newUsername = "unm";
        activatedUser.setUserName(newUsername);
        userService.update(activatedUser.getId(), activatedUser);
    }

    @After
    public void tearDown() {
        userRepository.delete(user);
        userRepository.delete(badUser);
        userRepository.delete(activatedUser);
    }
}
