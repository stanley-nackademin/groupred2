package se.backend.groupred2.model;

import javax.persistence.*;
import java.util.Collection;

@Entity
public final class Team {

    @Id
    @GeneratedValue
    private Long id;

    @Column(nullable = false)
    private String name;
    private boolean active;
    @OneToMany(mappedBy = "team")
    private Collection<User> user;

    protected Team() {
    }


    public Team(String name, boolean active) {
        this.name = name;
        this.active = active;

    }

    public void setActive(boolean active) {
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

    public Collection<User> getUser() {
        return user;
    }
    //TODO activate or deactivate a team ?
}
