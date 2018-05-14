package se.backend.groupred2.model;

import javax.persistence.*;
import java.util.Collection;
import java.util.List;

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
    private List<Task> tasks;

    protected User() {
    }

    public User(String firstName, String lastName, String userName, boolean active, Long userNumber) {

        this.firstName = firstName;
        this.lastName = lastName;
        this.userName = userName;
        this.active = active;
        this.userNumber = userNumber;
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

    public List<Task> getTasks() {
        return tasks;
    }

    public void setTasks(List<Task> tasks) {
        this.tasks = tasks;
    }

    //    public Collection<Task> getTasks() {
//        return tasks;
//    }

    public Team getTeam() {
        return team;
    }

    public void setTeam(Team team) {
        this.team = team;
    }

    //TODO add task / remove task / toString ?
}
