package se.backend.groupred2.model;

import org.springframework.beans.factory.annotation.Value;

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

    //sätt team inactive som default?
    private boolean active;

    //lättare om man någon gång i framtiden vill utöka antal users i ett team
    @Column(nullable = false)
    private int maxUsers;

    protected Team() {
    }

    public Team(String name, boolean active, int maxUsers) {
        this.name = name;
        this.setActive(active);
        this.maxUsers = maxUsers;
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

    public void setActive(boolean active) {
        this.active = active;
    }

    public int getMaxUsers() {
        return maxUsers;
    }

    public void setMaxUsers(int maxUsers) {
        this.maxUsers = maxUsers;
    }
}
