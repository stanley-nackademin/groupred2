package se.backend.groupred2.service;

import org.springframework.stereotype.Service;

import se.backend.groupred2.model.Task;
import se.backend.groupred2.model.TaskStatus;
import se.backend.groupred2.model.Team;
import se.backend.groupred2.model.User;
import se.backend.groupred2.repository.TaskRepository;
import se.backend.groupred2.repository.TeamRepository;
import se.backend.groupred2.repository.UserRepository;
import se.backend.groupred2.service.exceptions.InvalidTaskException;


import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public final class TaskService {

    private final TaskRepository taskRepository;
    private final UserRepository userRepository;
    private final TeamRepository teamRepository;

    public TaskService(TaskRepository taskRepository, UserRepository userRepository, TeamRepository teamRepository) {
        this.taskRepository = taskRepository;
        this.userRepository = userRepository;
        this.teamRepository = teamRepository;
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

    public Optional<Task> assignTaskToUser(Long id, User user) {
        Optional<Task> taskResult = taskRepository.findById(id);
        Optional<User> userResult = userRepository.findById(user.getId());

        List<Task> taskItems = taskRepository.findAllTaskByUserId(user.getId());

        System.out.println(user.getId() + " " + user.getFirstName());
        System.out.println(userResult.get().getFirstName());

        if (taskResult.isPresent() && userResult.isPresent()) {
            System.out.println("I is present");


            if (!userResult.get().isActive()) {
                System.out.println("I isActive: ");
                throw new InvalidTaskException("That user is not active");
            } else if (taskItems.size() > 4) {
                System.out.println("I size");
                throw new InvalidTaskException("To many tasks for that user, Max = 5");
            }
//            } else if (taskItems.size() > 4) {
//                throw new InvalidTaskException("To many tasks for that user, Max = 5");
//            } else if (taskItems.stream().anyMatch(t -> t.getId().equals(id))) {
//                throw new InvalidTaskException("That user already have that task");
//            }
            Task temp = taskResult.get();
            temp.setUser(userResult.get());

            taskRepository.save(temp);

            System.out.println("Sparat user");

            return taskResult;

        } else {
            throw new InvalidTaskException("Could not find a user or task");
        }

    }

    public List<Task> getAllTasksByStatus(String status) {
        TaskStatus stat = TaskStatus.valueOf(status);
        List<Task> tasks = taskRepository.findAllByStatus(stat);

//        tasks = tasks.stream()
//                .filter(task -> task.getStatus().equals(status))
//                .collect(Collectors.toList());

        if (tasks.isEmpty()) {
            throw new InvalidTaskException("Could not find any tasks with that status");
        }
        return tasks;
    }

    public List<Task> getAllTasksByUserId(Long userId) {
        List<Task> tasks = taskRepository.findAllTaskByUserId(userId);

        /*tasks = tasks.stream()
                .filter(task -> task.getUser().getId().equals(userId))
                .collect(Collectors.toList());*/

        if (tasks.isEmpty()) {
            throw new InvalidTaskException("Could not find any tasks for that user");
        }
        return tasks;
    }

//    public List<Task> getAllTasksByTeamId(Long teamId) {
//        List<User> users = userRepository.findUsersByTeamId(teamId);
//
//        if (users.isEmpty()) {
//            throw new InvalidTaskException("Could not find any tasks for that team");
//        }
//
//        return taskRepository.findAll().stream()
//                .filter(task -> task.getUser().getTeam().equals(teamId))
//                .collect(Collectors.toList());
//    }

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

    public List<Task> getAllTaskByUserId(Long userId) {
        return taskRepository.findAllTaskByUserId(userId);
    }

    public List<Task> getAllTasksByTeamId(Long teamId) {
        //Optional<Team> teamResult = teamRepository.findById(teamId);
        List<User> userResult = userRepository.findUsersByTeamId(teamId);  //spara alla users som tillhör detta team id.
        List<Task> allTasks = new ArrayList<>();                                                                 // för varje user från detta team, spara deras tasks
        //Returnera en lista på dessa tasks

        userResult.forEach(user -> allTasks.addAll(taskRepository.findAllTaskByUserId(user.getId())));

        for (Task item : allTasks) {
            System.out.println(item);
        }

        return allTasks;
    }
}



