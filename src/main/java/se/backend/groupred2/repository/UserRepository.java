package se.backend.groupred2.repository;

import org.springframework.data.repository.CrudRepository;
import se.backend.groupred2.model.User;

import java.util.List;
import java.util.Optional;

public interface UserRepository extends CrudRepository<User, Long> {


    List<User> findByUserNumber(Long userNumber);

    List<User> findUserByUserName(String userName);

    List<User> findUserByFirstName(String firstName);

    List<User> findUserByLastName(String lastName);

    Optional<User> findAllUserByTeamId(Long id);

}
