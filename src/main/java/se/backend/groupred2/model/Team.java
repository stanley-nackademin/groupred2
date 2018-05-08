package se.backend.groupred2.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;

@Entity
public final class Team {

    @Id
    @GeneratedValue
    private Long id;

    @Column(nullable = false)
    private String name;
    private boolean active;

    protected Team(){}

    public Team(String name, boolean active) {
        this.name = name;
        this.active = active;
    }

    public Long getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public boolean isActive() {
        return active;
    }

    //TODO activate or deactivate a team ?
}
