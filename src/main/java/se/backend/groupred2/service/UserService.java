package se.backend.groupred2.service;

import org.springframework.stereotype.Service;
import se.backend.groupred2.model.Task;
import se.backend.groupred2.model.TaskStatus;
import se.backend.groupred2.model.User;
import se.backend.groupred2.repository.TaskRepository;
import se.backend.groupred2.repository.UserRepository;
import se.backend.groupred2.service.exceptions.InvalidTeamException;
import se.backend.groupred2.service.exceptions.InvalidUserException;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public final class UserService {

    private final UserRepository repository;
    private final TaskRepository taskRepository;


    public UserService(UserRepository repository, TaskRepository taskRepository) {
        this.repository = repository;
        this.taskRepository = taskRepository;
    }

    public User createUser(User user) {
        validate(user);
        return repository.save(new User(user.getFirstName(), user.getLastName(),
                user.getUserName(), user.isActive(), user.getUserNumber()));
    }

    public Optional<User> update(Long id, User user) {
        validate(user);
        Optional<User> result = repository.findById(id);

        result.ifPresent(u -> {
            u.setFirstName(user.getFirstName());
            u.setLastName(user.getLastName());
            u.setUserName(user.getUserName());
            u.setActive(user.isActive());
            repository.save(u);
        });

        return result;
    }

    public Optional<User> deActivate(Long id) {
        Optional<User> result = repository.findById(id);

        result.ifPresent(t -> {
            checkIfActive(t);
            t.deActivate();
            List<Task> tasks = getAllTasksByUserId(result.get().getId());
            tasks.forEach(task -> task.setStatus(TaskStatus.UNSTARTED));
            tasks.forEach(task -> taskRepository.save(task));

            repository.save(result.get());
        });

        return result;
    }

    private void checkIfActive(User user) {
        if (!user.isActive())
            throw new InvalidTeamException("User is already inactive.");
    }

    public List<Task> getAllTasksByUserId(Long userkId) {
        return taskRepository.findAllByUser_Id(userkId);

    }

    public List<User> getUserByUserNamefirstNameLastName(Long userNumber, String userName, String firstName, String lastName) {

        if (userNumber == 0) {
            if (!userName.equals("0")) {
                List<User> user = repository.findUserByUserName(userName);
                return user;

            } else if ((!firstName.equals("0"))) {
                List<User> user = repository.findUserByFirstName(firstName);
                return user;
            } else if (!lastName.equals("0")) {

                List<User> user = repository.findUserByLastName(lastName);
                return user;
            }


        } else if (!(userNumber == 0)) {
            return repository.findByUserNumber(userNumber);

        } else {
            throw new InvalidUserException("fel");
        }
        return null;
    }

    public List<User> getAllUserByteamId(Long id) {

        List<User> user = repository.findUsersByTeamId(id);
        if (user.isEmpty())
            throw new InvalidUserException("Could not find any user");

        return repository.findAll().stream()
                .filter(t -> t.getTeam().getId().equals(id))
                .collect(Collectors.toList());
    }

    public void validate(User user) {
        int UserName = user.getUserName().length();
        if (UserName < 10) {
            throw new InvalidUserException("UserName must be atleast 10 characters");
        } else if (user.getUserName().isEmpty() && user.getUserName() == null) {
            throw new InvalidUserException("userName is Empty");

        }
    }

}


