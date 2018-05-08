//package se.backend.groupred2.model;
//
//import com.fasterxml.jackson.annotation.JsonCreator;
//
//import javax.persistence.*;
//
//@Entity
//public class User {
//
//
//    @Id
//    @GeneratedValue
//
//    private Long id;
//    @Column(nullable = false)
//    private String firstName;
//    @Column(nullable = false)
//    private String lastName;
//    private String status;
//    //@ManyToOne
//    //private Team team;
//
//    protected User(){}
//
//    @JsonCreator
//    public User( Long id ,String firstName, String lastName, String status ) {
//        this.id = id;
//        this.firstName = firstName;
//        this.lastName = lastName;
//        this.status = status;
//
//    }
//
//    public Long getId() {
//        return id;
//    }
//
//    public String getFirstName() {
//        return firstName;
//    }
//
//    public String getLastName() {
//        return lastName;
//    }
//
//    public String getStatus() {
//        return status;
//    }
//}
