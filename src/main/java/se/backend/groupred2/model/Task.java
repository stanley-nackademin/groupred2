package se.backend.groupred2.model;

import javax.persistence.*;

@Entity
public final class Task {

    @Id
    @GeneratedValue
    private Long id;

    @Column(nullable = false)
    private String name, text;

    @Column
    @Enumerated
    private TaskStatus status;

    @ManyToOne
    private User user;

    // Mappning mot team?

    protected Task() {
    }

    public Task(String name, String text, TaskStatus status, User user) {
        this.name = name;
        this.text = text;
        this.status = status;
        this.user = user;
    }

    public Long getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public String getText() {
        return text;
    }

    public TaskStatus getStatus() {
        return status;
    }

    public User getUser() {
        return user;
    }
}
