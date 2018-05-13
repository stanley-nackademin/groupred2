package se.backend.groupred2.service;

import org.springframework.stereotype.Service;
import se.backend.groupred2.model.TaskStatus;
import se.backend.groupred2.model.User;
import se.backend.groupred2.repository.TaskRepository;
import se.backend.groupred2.repository.TeamRepository;
import se.backend.groupred2.repository.UserRepository;

import java.util.List;
import java.util.Optional;

@Service
public final class UserService {

    private final UserRepository repository;
    private final TeamRepository repositoryTeam;
    private final TaskRepository taskRepository;


    public UserService(UserRepository repository, TeamRepository repositoryTeam, TaskRepository taskRepository) {
        this.repository = repository;
        this.repositoryTeam = repositoryTeam;
        this.taskRepository = taskRepository;
    }

    public Iterable<User> getAllUsers() {
        return repository.findAll();
    }

    public User createUser(User user) {
        validate(user);
        return repository.save(new User(user.getFirstName(), user.getLastName(),
                user.getUserName(), user.isActive(), user.getUserNumber()));
    }


    public Optional<User> updatUser(long id, User user) {
        validate(user);
        Optional<User> result = repository.findById(id);
        if (result.isPresent()) {
            User userupdate = result.get();
            userupdate.setFirstName(user.getFirstName());
            userupdate.setLastName(user.getLastName());
            userupdate.setUserName(user.getUserName());
            userupdate.setUserNumber(user.getUserNumber());
            userupdate.setActive(user.isActive());

            if (userupdate.isActive() == false) {
                System.out.println("tasks");
                ValidateInactiverasUser(user);


            }

            return Optional.of(repository.save(userupdate));

        }


        return Optional.empty();

    }


    public List<User> getUserByUserNamefirstNameLastName(Long userNumber, String userName, String firstName, String lastName) {

        if (userNumber == 0) {
            if (!userName.equals("0")) {
                List<User> user2 = repository.findUserByUserName(userName);
                return user2;
            } else if ((!firstName.equals("0"))) {
                List<User> user = repository.findUserByFirstName(firstName);
                return user;
            } else if (!lastName.equals("0")) {

                List<User> user1 = repository.findUserByLastName(lastName);
                return user1;
            }


        } else if (!(userNumber == 0)) {
            return repository.findByUserNumber(userNumber);

        }
        return null;
    }

    public Optional<User> getALLUserByteamId(Long teamId) {

        Optional<User> user = repository.findAllUserByTeamId(teamId);
        if (user.isPresent()) {
            return user;
        } else {
            return Optional.empty();
        }
    }

//
////    public Optional<User> updatUserInactiv(long id, User user) {
////        Optional<User> result = repository.findById(id);
////        if (result.isPresent()) {
////            User userupdateInactiv = result.get();
////            if (user.isActive() == true) {
////                userupdateInactiv.setActive(false);
////                return Optional.of(repository.save(userupdateInactiv));
////            }
////        }
////
////        return Optional.empty();
////    }

    private void validate(User user) {
        int UserName = user.getUserName().length();
        if (UserName < 10) {
            throw new InvalidUserException("UserName är minst än 10 token");
        }


    }

    private void ValidateInactiverasUser(User user) {
        user.getTasks().forEach(task -> {
            task.setStatus(TaskStatus.UNSTARTED);
        });
    }






}


//    public List<User> getUser(long userNumber) {
//        List<User> userList = new ArrayList<>();
//
//        if (userNumber == 0) {
//            repository.findAll().forEach(t -> userList.add(t));
//            return userList;
//        } else if (!(userNumber == 0)) {
//            return repository.findByUserNumber(userNumber);
//        }
//        return null;
//    }
