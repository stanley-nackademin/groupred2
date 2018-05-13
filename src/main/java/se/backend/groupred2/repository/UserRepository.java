package se.backend.groupred2.repository;

import org.springframework.data.repository.CrudRepository;
import se.backend.groupred2.model.User;

import java.util.List;

public interface UserRepository extends CrudRepository<User, Long> {

    List<User> findUsersByTeamId(Long id);
}
