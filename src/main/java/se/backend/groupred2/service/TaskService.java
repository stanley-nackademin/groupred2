package se.backend.groupred2.service;

import org.springframework.stereotype.Service;
import se.backend.groupred2.model.Task;
import se.backend.groupred2.model.TaskStatus;
import se.backend.groupred2.model.User;
import se.backend.groupred2.repository.TaskRepository;
import se.backend.groupred2.repository.UserRepository;
import se.backend.groupred2.service.exceptions.InvalidTaskException;

import javax.ws.rs.core.Response;
import java.util.*;
import java.util.stream.Collectors;

@Service
public final class TaskService {

    private final TaskRepository taskRepository;
    private final UserRepository userRepository;

    public TaskService(TaskRepository taskRepository, UserRepository userRepository) {
        this.taskRepository = taskRepository;
        this.userRepository = userRepository;
    }

    public Task createTask(Task task) {
        validateTask(task);
        return taskRepository.save(new Task(task.getTitle(), task.getDescription(), task.getStatus()));
    }

    public Optional<Task> getTask(Long id) {
        return taskRepository.findById(id);
    }

    public Optional<Task> deleteTask(Long id) {
        Optional<Task> temp = taskRepository.findById(id);

        if (temp.isPresent()) {
            taskRepository.deleteById(id);
        }
        return temp;
    }

    public Optional<Task> updateStatus(Long id, Task task) {
        Optional<Task> taskResult = taskRepository.findById(id);

        if (taskResult.isPresent()) {
            Task updatedTask = taskResult.get();

            updatedTask.setStatus(task.getStatus());
            taskRepository.save(updatedTask);
        } else {
            throw new InvalidTaskException("Could not find that task");
        }
        return taskResult;
    }

    public Optional<Task> assignTaskToUser(Long id, Long userId) {
        Optional<Task> taskResult = taskRepository.findById(id);
        Optional<User> userResult = userRepository.findById(userId);

        List<Task> taskItems = taskRepository.findAllTaskByUserId(userId);

        if (taskResult.isPresent() && userResult.isPresent()) {
            if (!userResult.get().isActive()) {
                throw new InvalidTaskException("That user is not active");
            } else if (taskItems.size() > 4) {
                throw new InvalidTaskException("To many tasks for that user, Max = 5");
            }

            Task temp = taskResult.get();
            temp.setUser(userResult.get());
            taskRepository.save(temp);

            return taskResult;
        } else {
            throw new InvalidTaskException("Could not find a user or task");
        }
    }

    public List<Task> getAllTasksByStatus(String status) {
        validateStatus(status);
        List<Task> tasks = taskRepository.findAllByStatus(TaskStatus.valueOf(status));

        if (tasks.isEmpty()) {
            throw new InvalidTaskException("Could not find any tasks with that status");
        }
        return tasks;
    }

    public List<Task> getAllTasksByUserId(Long userId) {
        List<Task> tasks = taskRepository.findAllTaskByUserId(userId);

        if (tasks.isEmpty()) {
            throw new InvalidTaskException("Could not find any tasks for that user");
        }
        return tasks;
    }

    public List<Task> getAllTasksByDescription(String description) {
        List<Task> tasks = taskRepository.findAll();

        tasks = tasks.stream()
                .filter(task -> task.getDescription().contains(description))
                .collect(Collectors.toList());

        if (tasks.isEmpty()) {
            throw new InvalidTaskException("Could not find any tasks with that description");
        }
        return tasks;
    }

    public List<Task> getAllTasksByTeamId(Long teamId) {
        List<User> userResult = userRepository.findUsersByTeamId(teamId);
        if (userResult.isEmpty()) {
            throw new InvalidTaskException("Could not find any users for that team");
        }
        List<Task> allTasks = new ArrayList<>();
        userResult.forEach(user -> allTasks.addAll(taskRepository.findAllTaskByUserId(user.getId())));

        return allTasks;
    }

    private void validateTask(Task task) {
        if (task.getTitle().isEmpty() || task.getTitle() == null || task.getDescription() == null || task.getStatus() == null) {
            throw new InvalidTaskException("Title, description and status have to have values. Can not leave empty");
        }
    }

    public void validateStatus(String status) {
        if (!status.equals("UNSTARTED") && !status.equals("STARTED") && !status.equals("DONE")) {
            throw new InvalidTaskException("Incorrect status, have to be UNSTARTED, STARTED or DONE");
        }
    }

    public Optional<Task> assignHelperToTask(Long id, Long usernumber) {
        Optional<Task> taskResult = taskRepository.findById(id);
        List<User> myList = userRepository.findByUserNumber(usernumber);

        if(taskResult.isPresent() && !myList.isEmpty()){
            Task finalResult = taskResult.get();
            Set<User> mySet = taskResult.get().getHelpers();
            mySet.addAll(myList);

            finalResult.setHelpers(mySet);
            taskRepository.save(finalResult);
        }else {
            throw new InvalidTaskException("Could not find a task or user!");
        }

        return taskResult;
    }
}