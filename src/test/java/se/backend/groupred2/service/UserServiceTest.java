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

@RunWith(SpringRunner.class)
@SpringBootTest
public class UserServiceTest {

    @Autowired
    UserService userService;

    @Autowired
    TeamRepository teamRepository;

    @Autowired
    UserRepository userRepository;

    User user = new User("myFirstname", "myLastname", "MyUsernameee", true, 112233L);
    User badUser = new User("myFirstname", "myLastname", "tooShort", true, 11223L);

    @Before
    public void setUp() {
        userRepository.save(user);
    }

    @Test
    public void createUserTest() throws Exception {
        List<User> result = userRepository.findByUserNumber(112233L);
        assertEquals(!result.isEmpty(), !result.isEmpty());
    }

    @Test(expected = InvalidUserException.class)
    public void createUserTestBadUsernumber() throws Exception {
        userService.validate(badUser);
    }

    @After
    public void tearDown() {
        userRepository.delete(user);
        userRepository.delete(badUser);
    }

}
