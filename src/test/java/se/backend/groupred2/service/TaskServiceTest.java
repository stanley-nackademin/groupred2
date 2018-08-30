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

import java.util.Optional;

import static org.junit.Assert.assertEquals;

@RunWith(SpringRunner.class)
@SpringBootTest
public class TaskServiceTest {

    @Autowired
    TaskService taskService;

    @Autowired
    TaskRepository taskRepository;

    Task task;

    @Before
    public void setUp() {
        task = new Task("TaskTest", "test the createTask method", TaskStatus.UNSTARTED);
    }

    @Test
    public void createTaskTest() throws Exception {
        taskService.createTask(task);
        task.setId(1L);

        Optional<Task> result = taskRepository.findById(task.getId());
        Task taskFromDatabase = result.get();

        assertEquals(task.toString(), taskFromDatabase.toString());
    }

    @After
    public void tearDown() {
        taskRepository.delete(task);
    }
}
