package se.backend.groupred2.model;

import javax.persistence.*;
import javax.ws.rs.DefaultValue;
import java.util.Collection;

@Entity
public final class Team {

    @Id
    @GeneratedValue
    private Long id;

    @Column(nullable = false)
    private String name;

    //sätt team som inactive som default?
    private boolean active;
    //@OneToMany(mappedBy = "team")
    //private Collection<User> users;

    protected Team() {
    }


    public Team(String name, boolean active) {
        this.name = name;
        this.setActive(active);
    }

    public Long getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    //public Collection<User> getUsers() {
    //    return users;
    //}

    public boolean isActive() {
        return active;
    }

    public void setActive(boolean active) {
        this.active = active;
    }
}
