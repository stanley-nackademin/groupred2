package se.backend.groupred2.service;

import org.springframework.stereotype.Service;
import se.backend.groupred2.model.Task;
import se.backend.groupred2.model.User;
import se.backend.groupred2.repository.TaskRepository;
import se.backend.groupred2.repository.UserRepository;
import se.backend.groupred2.service.exceptions.InvalidTaskException;


import java.util.List;
import java.util.Optional;
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
        validate(task);
        return taskRepository.save(new Task(task.getTitle(), task.getDescription(), task.getStatus()));
        // return repository.save(task);  // räcker att bara spara task?
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

    public Optional<Task> assignTaskToUser(Long id, Long userId) {
        Optional<Task> task = taskRepository.findById(id);
        Optional<User> user = userRepository.findById(userId);

        List<Task> taskItems = taskRepository.findAll().stream()
                .filter(t -> t.getUser().getId().equals(userId))
                .collect(Collectors.toList());

        if (task.isPresent() && user.isPresent()) {

            if (!user.get().isActive()) {
                throw new InvalidTaskException("That user is not active");
            } else if (taskItems.size() > 4) {
                throw new InvalidTaskException("To many tasks for that user, Max = 5");
            } else if (taskItems.stream().anyMatch(t -> t.getId().equals(id))) {
                throw new InvalidTaskException("That user already have that task");
            }
            Task temp = task.get();
            temp.setUser(user.get());
            taskRepository.save(temp);

        } else {
            throw new InvalidTaskException("Could not find a user or task");
        }
        return task;
    }

//    public Optional<Task> updateTask(Long id, Task task) {
//        Optional<Task> result = taskRepository.findById(id);
//        System.out.println("Service: början");
//        if (result.isPresent()) {
//            Task updatedTask = result.get();
//
//            if (!updatedTask.getTitle().isEmpty()) {
//                updatedTask.setTitle(task.getTitle());
//            }
//            if (updatedTask.getDescription() != null) {
//                updatedTask.setDescription(task.getDescription());
//            }
//            if (updatedTask.getStatus() != null) {
//                updatedTask.setStatus(task.getStatus());
//            }
//
//            System.out.println("Service: i IFn " + "updatedTask: " + updatedTask.getTitle()
//                    + updatedTask.getDescription() + updatedTask.getStatus());
//
//            return Optional.ofNullable(taskRepository.save(updatedTask));
//        }
//
//        System.out.println("Service: utanför IFn");
//        return Optional.empty();
//    }


    public List<Task> getAllTasksByStatus(String status) {
        List<Task> tasks = taskRepository.findAll();

        tasks = tasks.stream()
                .filter(task -> task.getStatus().equals(status))
                .collect(Collectors.toList());

        if (tasks.isEmpty()) {
            throw new InvalidTaskException("Could not find any tasks with that status");
        }
        return tasks;
    }

    public List<Task> getAllTasksByUserId(Long userId) {
        List<Task> tasks = taskRepository.findAll();

        tasks = tasks.stream()
                .filter(task -> task.getUser().getId().equals(userId))
                .collect(Collectors.toList());

        if (tasks.isEmpty()) {
            throw new InvalidTaskException("Could not find any tasks for that user");
        }
        return tasks;
    }

    public List<Task> getAllTasksByTeamId(Long teamId) {
        List<User> users = userRepository.findUsersByTeamId(teamId);

        if (users.isEmpty()) {
            throw new InvalidTaskException("Could not find any tasks for that team");
        }

        return taskRepository.findAll().stream()
                .filter(task -> task.getUser().getTeam().equals(teamId))
                .collect(Collectors.toList());
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

    private void validate(Task task) {
        if (task.getTitle().isEmpty() || task.getTitle() == null || task.getDescription() == null || task.getStatus() == null) {
            throw new InvalidTaskException("Title, description and status have to have values. Can not leave empty");
        }
    }
}



