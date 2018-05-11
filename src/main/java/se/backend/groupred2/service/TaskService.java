package se.backend.groupred2.service;

import org.springframework.stereotype.Service;

import se.backend.groupred2.model.Task;
import se.backend.groupred2.repository.TaskRepository;

@Service
public final class TaskService {

    private final TaskRepository repository;

    public TaskService(TaskRepository repository) {
        this.repository = repository;
    }

    public Task createTask(Task task) {
        //validate(user);
        return repository.save(new Task(task.getTitle(), task.getText(),
                task.getStatus()));


        // ska vi göra en klass som tar hand om hur man skapar nya users/tasks/teams? blir så lång kod annars.
    }
}



