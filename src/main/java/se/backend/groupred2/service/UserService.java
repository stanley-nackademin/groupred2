package se.backend.groupred2.service;

import org.springframework.stereotype.Service;
import se.backend.groupred2.model.Task;
import se.backend.groupred2.model.TaskStatus;
import se.backend.groupred2.model.Team;
import se.backend.groupred2.model.User;
import se.backend.groupred2.repository.TaskRepository;
import se.backend.groupred2.repository.TeamRepository;
import se.backend.groupred2.repository.UserRepository;
import se.backend.groupred2.service.exceptions.InvalidTeamException;
import se.backend.groupred2.service.exceptions.InvalidUserException;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import static org.apache.commons.lang3.StringUtils.isBlank;

@Service
public final class UserService {

    private final UserRepository repository;
    private final TaskRepository taskRepository;
    private final TeamRepository teamRepository;


    public UserService(UserRepository repository, TaskRepository taskRepository, TeamRepository teamRepository) {
        this.repository = repository;
        this.taskRepository = taskRepository;
        this.teamRepository = teamRepository;
    }

    public User createUser(User user) {
        validate(user);
        return repository.save(new User(user.getFirstName(), user.getLastName(),
                user.getUserName(), user.getIsActive(), user.getUserNumber()));
    }

    public Optional<User> update(Long id, User user) {
        Optional<User> result = repository.findById(id);

        if (result.isPresent()) {
            User updatedUser = result.get();

            if (!isBlank(user.getFirstName())) {
                updatedUser.setFirstName(user.getFirstName());
            }

            if (!isBlank(user.getLastName())) {
                updatedUser.setLastName(user.getLastName());
            }

            if (user.getIsActive() != null) {
                updatedUser.setIsActive(user.getIsActive());
            }

            if (user.getUserNumber() != null) {
                List<User> users = repository.findAll().stream().filter(u -> u.getUserNumber().equals(user.getUserNumber())).collect(Collectors.toList());
                if (!users.isEmpty()) {
                    throw new InvalidUserException("UserNumber is already in use");
                }
                updatedUser.setUserNumber(user.getUserNumber());
            }

            if (!isBlank(user.getUserName())) {
                if (user.getUserName().length() < 10) {
                    throw new InvalidUserException("UserName must be atleast 10 characters");
                }

                updatedUser.setUserName(user.getUserName());
            }

            return Optional.of(repository.save(updatedUser));
        }

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

    public void checkIfActive(User user) {
        if (!user.getIsActive()) {
            throw new InvalidTeamException("User is already inactive.");
        }
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
        Optional<Team> result = teamRepository.findById(id);
        List<User> users;

        if (result.isPresent()) {
            Team team = result.get();
            users = repository.findUsersByTeams(team);

            if (users.isEmpty()) {
                throw new InvalidUserException("Could not find any user");
            }
        } else {
            throw new InvalidUserException("Could not find team from given team-ID");
        }

        return users;
    }

    public void validate(User user) {

        List<User> result = repository.findAll().stream().filter(u -> u.getUserNumber().equals(user.getUserNumber())).collect(Collectors.toList());
        if (!result.isEmpty()) {
            throw new InvalidUserException("UserNumber is already in use");
        }

        int UserName = user.getUserName().length();
        if (UserName < 10) {
            throw new InvalidUserException("UserName must be atleast 10 characters");
        } else if (user.getUserName().isEmpty() && user.getUserName() == null) {
            throw new InvalidUserException("userName is Empty");
        }
    }
}
