package se.backend.groupred2.repository;

import org.springframework.data.repository.CrudRepository;
import se.backend.groupred2.model.WorkItem;

public interface WorkItemRepository extends CrudRepository<WorkItem, Long>{
}
