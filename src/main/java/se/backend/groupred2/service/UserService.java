package se.backend.groupred2.service;

import org.springframework.stereotype.Service;
import se.backend.groupred2.model.User;
import se.backend.groupred2.repository.UserRepository;

@Service
public final class UserService {

    private final UserRepository repository;

    public UserService(UserRepository repository) {
        this.repository = repository;
    }

    public User createUser(User user){
        //validate(user);
        return repository.save(new User(user.getFirstName(), user.getLastName(),
                user.getUserName(), user.isActive(), user.getUserNumber()));  //user.getTeam()

        // ska vi göra en klass som tar hand om hur man skapar nya users/tasks/teams? blir så lång kod annars.
    }
}
