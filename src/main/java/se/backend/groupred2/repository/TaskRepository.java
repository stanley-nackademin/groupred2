package se.backend.groupred2.repository;

import org.springframework.data.repository.CrudRepository;
import se.backend.groupred2.model.Task;

public interface TaskRepository extends CrudRepository<Task, Long> {
}
