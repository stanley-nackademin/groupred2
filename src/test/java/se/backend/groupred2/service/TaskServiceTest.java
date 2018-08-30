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
import se.backend.groupred2.repository.UserRepository;
import se.backend.groupred2.service.exceptions.InvalidTaskException;
import se.backend.groupred2.service.exceptions.InvalidUserException;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotEquals;

@RunWith(SpringRunner.class)
@SpringBootTest
public class TaskServiceTest {

    @Autowired
    TaskService taskService;

    @Autowired
    TaskRepository taskRepository;

    @Autowired
    UserService userService;

    @Autowired
    UserRepository userRepository;

    Task task;
    User activeUser1;
    User activeUser2;
    User inactiveUser;
    Task testTask;
    List<Task> taskList = new ArrayList<>();

    @Before
    public void setUp() {
        task = new Task("TaskTest", "test the createTask method", TaskStatus.UNSTARTED);
        testTask = taskService.createTask(new Task("testTask", "test task", TaskStatus.UNSTARTED));
        activeUser1 = userService.createUser(new User("fn", "ln", "someCrappyUsername", true, 1000L));
        activeUser2 = userService.createUser(new User("fname", "lname", "newCrappyUsername", true, 1001L));
        inactiveUser = userService.createUser(new User("somefn", "someln", "somenewUsername", false, 1002L));

        for(int i=0; i<6; i++){
            Task task = taskService.createTask(new Task("task"+i, "title"+i, TaskStatus.UNSTARTED));
            taskList.add(task);
        }
    }

    //Todo: FAILED - NoSuchElementException - should pass
    @Test
    public void createTaskTest() throws Exception {
        taskService.createTask(task);
        task.setId(1L);

        Optional<Task> result = taskRepository.findById(task.getId());
        Task taskFromDatabase = result.get();

        assertEquals(task.toString(), taskFromDatabase.toString());
    }

    @Test
    public void assignValidTaskToValidUserTest(){
        taskService.assignTaskToUser(testTask.getId(), activeUser1.getId());

        assertEquals(taskService.getTask(testTask.getId()).get().getUser().getId(), activeUser1.getId());
        assertNotEquals(taskService.getTask(testTask.getId()).get().getUser().getId(), activeUser2.getId());
    }

    @Test (expected = InvalidTaskException.class)
    public void assignValidTaskToInactiveUserTest(){
        taskService.assignTaskToUser(testTask.getId(), inactiveUser.getId());
    }

    @Test (expected = InvalidTaskException.class)
    public void assignValidTaskToUserWith5TasksTest(){

        for(int i=0; i<taskList.size(); i++){
            taskService.assignTaskToUser(taskList.get(i).getId(), activeUser2.getId());
        }
    }

    @After
    public void tearDown(){
        taskRepository.delete(task);
        taskRepository.delete(testTask);
        userRepository.delete(inactiveUser);

        for(Task t : taskList){
            taskRepository.delete(t);
        }

        userRepository.delete(activeUser1);
        userRepository.delete(activeUser2);
    }
}