package se.backend.groupred2.service;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;
import se.backend.groupred2.model.Task;
import se.backend.groupred2.model.TaskStatus;
import se.backend.groupred2.model.User;
import se.backend.groupred2.repository.TaskRepository;
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
    TaskService taskService;

    @Autowired
    TaskRepository taskRepository;

    @Autowired
    UserRepository userRepository;

    ArrayList<User> users = new ArrayList<>();
    private User testUser;
    private User activatedUser;
    private User deActivatedUser;
    private User user = new User("myFirstname", "myLastname", "MyUsernameee", true, 112233L);
    private User badUser = new User("myFirstname", "myLastname", "tooShort", true, 11223L);
    private Task task = new Task("FirstTask", "test the createTask method", TaskStatus.UNSTARTED);

    @Before
    public void setUp() {
        userRepository.save(user);
        userRepository.save(badUser);

        for (int i = 0; i < 5; i++){
            users.add(new User("activeUser"+i, "firstName"+i, "lastName"+i, true, 1000L+i));
        }
        for (User s: users) {
            userRepository.save(s);
        }

        task = new Task("FirstTask", "test the createTask method", TaskStatus.UNSTARTED);
        taskService.createTask(task);
        Optional<Task> taskWithId = taskRepository.findByTitle(task.getTitle());
        task.setId(taskWithId.get().getId());

        testUser = userRepository.findById(users.get(0).getId()).get();
        activatedUser = new User("activatedUser", "activatedUser", "activatedUser", true, 1L);
        deActivatedUser = new User("deActivatedUser", "deActivatedUser", "deActivatedUser", false, 2L);
        userRepository.save(activatedUser);
        userRepository.save(deActivatedUser);
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

        assertEquals(checkUser.getIsActive(), updatedUser.getIsActive());
        assertNotEquals(checkUser.getIsActive(), testUser.getIsActive());
    }

    @Test (expected = InvalidUserException.class)
    public void updateUserWithInvalidInputTest(){
        User updatedUser = new User("fn", "ln", "uname", false, testUser.getUserNumber());

        userService.update(testUser.getId(), updatedUser);
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

    @Test
    public void createTaskTest() {
        System.out.println(task.toString());
        Optional<Task> result = taskService.getTask(task.getId());
        Task taskFromDatabase = result.get();
        assertEquals(task.toString(), taskFromDatabase.toString());
        assertEquals(task.getDescription(), taskFromDatabase.getDescription());
        assertEquals(task.getTitle(), taskFromDatabase.getTitle());
        assertEquals(task.getStatus(), taskFromDatabase.getStatus());
    }

    @After
    public void tearDown() {

        for (User s: users) {
            userRepository.delete(s);
        }

        userRepository.delete(user);
        userRepository.delete(badUser);
        userRepository.delete(activatedUser);
        userRepository.delete(deActivatedUser);
        taskRepository.delete(task);
    }
}
