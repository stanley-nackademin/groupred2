package se.backend.groupred2.model;

import javax.persistence.*;
import java.util.List;

@Entity
public final class Team {


    @Id
    @GeneratedValue
    private Long id;

    @Column(nullable = false)
    private String name;

    //sÃ¤tt team inactive som default?
    private boolean active;

    //lÃ¤ttare om man nÃ¥gon gÃ¥ng i framtiden vill utÃ¶ka antal users i ett team
    @Column(nullable = false)
    private int maxUsers;
    @OneToMany
    List<User> users;

    protected Team() {
    }

    public Team(String name, boolean active, int maxUsers) {
        this.name = name;
        this.active = active;
        this.maxUsers = maxUsers;
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

    public void deActivate() {
        this.active = false;
    }

    public boolean isActive() {
        return active;
    }

    public int getMaxUsers() {
        return maxUsers;
    }

    public void setMaxUsers(int maxUsers) {
        this.maxUsers = maxUsers;
    }

    public void setName(String name) {
        this.name = name;
    }
}
