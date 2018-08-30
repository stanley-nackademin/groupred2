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
import se.backend.groupred2.repository.TaskRepository;
import se.backend.groupred2.service.exceptions.InvalidTaskException;

import java.util.Optional;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotEquals;

@RunWith(SpringRunner.class)
@SpringBootTest
public class TaskServiceTest {

    private Task taskForEquals;
    private Task taskForNotEquals;
    private Task invalidTaskNull;
    private Task invalidTask;

    @Autowired
    TaskService taskService;

    @Autowired
    TaskRepository taskRepository;

    @Before
    public void setUp() {
        taskForEquals = new Task("taskForEquals", "test the createTask method", TaskStatus.UNSTARTED);
        taskForNotEquals = new Task("taskForNotEquals", "test the createTask method", TaskStatus.UNSTARTED);
        invalidTaskNull = new Task("InvalidTask", "this task has invalid data", null);
        invalidTask = new Task("InvalidTaskNotNull", "this task has invalid data that is not null", TaskStatus.TEST);
    }


    @Test
    public void createTaskTestValidData() {
        taskForEquals.setId(1L);
        taskService.createTask(taskForEquals);

        taskForNotEquals.setId(2L);
        taskService.createTask(taskForNotEquals);

        Optional<Task> result = taskRepository.findById(taskForEquals.getId());
        Task taskFromDatabase = result.get();

        assertEquals(taskForEquals.toString(), taskFromDatabase.toString());
        assertNotEquals(taskForNotEquals.toString(), taskFromDatabase.toString());
    }

    /*Test passes when InvalidTaskException is thrown
     * which happens when TaskStatus = null*/
    @Test(expected = InvalidTaskException.class)
    public void createTaskTestInvalidData() {
        taskService.validateTask(invalidTaskNull);
    }

    /*Test passes when InvalidTaskException is thrown
     * which happens when TaskStatus has incorrect status type*/
    @Test(expected = InvalidTaskException.class)
    public void createTaskTestInvalidDataString() {
        taskService.validateStatus(invalidTask.getStatus().name());
    }

    @After
    public void tearDown(){
        taskRepository.delete(taskForEquals);
        taskRepository.delete(taskForNotEquals);
        taskRepository.delete(invalidTaskNull);
        taskRepository.delete(invalidTask);
    }
}
