package se.backend.groupred2.model;

import org.hibernate.annotations.NotFound;
import org.hibernate.annotations.NotFoundAction;

import javax.persistence.*;
import java.util.Collection;

@Entity
public final class Team {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String name;

    @Column(nullable = false)
    private boolean active;

    @Column(nullable = false, columnDefinition = "int default 10")
    private int maxUsers;

    @ManyToMany(mappedBy = "teams", fetch = FetchType.EAGER)
    @NotFound(action = NotFoundAction.IGNORE)
    private Collection<User> users;

    protected Team() {
    }

    public Team(String name, boolean active, int maxUsers) {
        this.name = name;
        this.active = active;
        this.maxUsers = maxUsers;
    }

    public void addUser(User user) {
        users.add(user);
    }

    public Collection<User> getAllUsers() {
        return users;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
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

    @Override
    public String toString() {
        return "Team{" +
                "id=" + id +
                ", name='" + name + '\'' +
                '}';
    }
}
