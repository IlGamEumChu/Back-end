package com.ilgamumchu.demar.domain;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Entity
@Getter
@Setter
@Table(name="user")
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "user_id")
    private Long id;

    @Column(nullable = false)
    private String name;

    @Column(nullable = false)
    private String email;

    @Column(length = 100)
    private String password;


    private String sp_id;

    private String sp_password;

    @OneToMany(cascade = CascadeType.ALL, mappedBy = "user")
    private List<Diary> diary = new ArrayList<>();

    //@Enumerated(EnumType.STRING)
    //private Role role;

    @Temporal(TemporalType.TIMESTAMP)
    private Date created_at;
}
