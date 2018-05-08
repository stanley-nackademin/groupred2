package se.backend.groupred2.model;

import javax.persistence.*;
import java.util.Collection;

@Entity
public final class User {

    @Id
    @GeneratedValue  //(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String firstName, lastName, userName;
    private boolean active;

    @Column(nullable = false, unique = true)
    private Long userNumber;

    @ManyToOne //(fetch = FetchType.LAZY)
    @JoinColumn
    private Team team;

    @OneToMany(mappedBy = "user")
    private Collection<Task> tasks;

    protected User(){}

    public User(String firstName, String lastName, String userName, boolean active, Long userNumber) {
        this.firstName = firstName;
        this.lastName = lastName;
        this.userName = userName;
        this.active = active;
        this.userNumber = userNumber;
    }

    public User(String firstName, String lastName, String userName, boolean active, Long userNumber, Team team) {
        this.firstName = firstName;
        this.lastName = lastName;
        this.userName = userName;
        this.active = active;
        this.userNumber = userNumber;
        this.team = team;
    }

    public Long getId() {
        return id;
    }

    public String getFirstName() {
        return firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public String getUserName() {
        return userName;
    }

    public boolean isActive() {
        return active;
    }

    public Long getUserNumber() {
        return userNumber;
    }

    public Team getTeam() {
        return team;
    }

    public Collection<Task> getTasks() {
        return tasks;
    }

    //TODO add task / remove task / toString ?
}
