package se.backend.groupred2.model;

import javax.persistence.*;

@Entity
public final class User {

    @GeneratedValue
    private Long id;

    @Column(nullable = false)
    private String firstName;

    @Column(nullable = false)
    private String lastName;

}
