package se.backend.groupred2.repository;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;
import se.backend.groupred2.model.Task;

import java.util.List;
import java.util.Optional;

@Repository
public interface TaskRepository extends CrudRepository<Task, Long> {


    List<Task> findAllTaskByUserId(Long userId);

    Optional<Task> findById(Long id);

    Optional<Task> findByTitle(String title);

    List<Task> findAllByStatus(Enum status);

    List<Task> findAll();

    List<Task> findAllByUser_Id(Long userId);

}
