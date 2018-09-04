package se.backend.groupred2.model;

import com.fasterxml.jackson.annotation.JsonBackReference;

import javax.persistence.*;
import java.util.Collection;

@Entity
public final class User {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String firstName, lastName, userName;

    private Boolean active;

    @Column(nullable = false, unique = true)
    private Long userNumber;

    @ManyToMany(fetch = FetchType.EAGER, cascade = {CascadeType.PERSIST, CascadeType.MERGE})
    @JoinTable(name = "user_teams",
            joinColumns = {@JoinColumn(name = "user_id")},
            inverseJoinColumns = {@JoinColumn(name = "teams_id")})
    @JsonBackReference
    private Collection<Team> teams;

    protected User() {
    }

    public User(String firstName, String lastName, String userName, Boolean active, Long userNumber) {

        this.firstName = firstName;
        this.lastName = lastName;
        this.userName = userName;
        this.active = active;
        this.userNumber = userNumber;
    }

    public void addTeam(Team team) {
        teams.add(team);
    }

    public Collection<Team> getTeams() {
        return teams;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
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

    public Long getUserNumber() {
        return userNumber;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public void setActive(boolean active) {
        this.active = active;
    }

    public void setUserNumber(Long userNumber) {
        this.userNumber = userNumber;
    }

    public void deActivate() {
        this.active = false;
    }

    public Boolean getIsActive() {
        return active;
    }

    public void setIsActive(Boolean active) {
        this.active = active;
    }

    @Override
    public String toString() {
        return "User{" +
                "id=" + id +
                ", firstName='" + firstName + '\'' +
                ", lastName='" + lastName + '\'' +
                ", userName='" + userName + '\'' +
                ", active=" + active +
                ", userNumber=" + userNumber +
                '}';
    }
}
