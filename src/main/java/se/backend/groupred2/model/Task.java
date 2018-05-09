package se.backend.groupred2.model;

import javax.persistence.*;

@Entity
public final class Task {

    @Id
    @GeneratedValue
    private Long id;

    @Column(nullable = false)
    private String title, text;

    @Column
    @Enumerated
    private TaskStatus status;

    @ManyToOne
    private User user;

    // Mappning mot team?

    protected Task() {
    }

    public Task(String title, String text, TaskStatus status) {
        this.title = title;
        this.text = text;
        this.status = status;

    }

    public Long getId() {
        return id;
    }

    public String getTitle() {
        return title;
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
