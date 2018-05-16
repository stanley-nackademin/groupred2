package se.backend.groupred2.model;

import org.springframework.lang.Nullable;

import javax.persistence.*;

@Entity
public class Issue {

    @Id
    @GeneratedValue(strategy=GenerationType.AUTO)
    private long id;

    @ManyToOne
    @JoinColumn
    private Task task;

//    @Column(nullable = false)
//    private String title;

    @Column(nullable = false)
    private String description;

    protected Issue() {}

    public Issue(long id, Task task, String description) {
        this.id = id;
        this.task = task;
        this.description = description;
    }

    public long getId() {
        return id;
    }

    public Task getTask() {
        return task;
    }

    public String getDescription() {
        return description;
    }

    public void setTask(Task task) {
        this.task = task;
    }

    public void setDescription(String description) {
        this.description = description;
    }
}
