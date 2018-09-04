package se.backend.groupred2.service;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;
import se.backend.groupred2.model.Task;
import se.backend.groupred2.model.User;
import se.backend.groupred2.repository.TaskRepository;
import se.backend.groupred2.repository.UserRepository;

import java.util.HashSet;
import java.util.Optional;
import java.util.Set;

import static org.junit.Assert.assertTrue;
import static se.backend.groupred2.model.TaskStatus.STARTED;

@RunWith(SpringRunner.class)
@SpringBootTest
public class TaskServiceTest {

    @Autowired
    TaskService taskService;

    @Autowired
    TaskRepository taskRepository;

    @Autowired
    UserRepository userRepository;

    private Task task = new Task("myTask", "Description", STARTED);
    private User user = new User("myFirstname", "myLastname", "MyUsernameee", true, 112233L);


    @Before
    public void setUp() {
        taskRepository.save(task);
        userRepository.save(user);
    }

    @Test
    public void assignHelperToTaskTest() throws Exception {
        Optional<Task> u = taskService.assignHelperToTask(task.getId(), user.getUserNumber());

        Set<User> mySet = new HashSet<>();
        mySet.addAll(u.get().getHelpers());

        for (User userLoop : mySet) {
            assertTrue(userLoop.toString().equals(user.toString()));
        }
    }

    @After
    public void tearDown() {
        taskRepository.delete(task);
        userRepository.delete(user);
    }
}
