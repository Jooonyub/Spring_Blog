package com.hanghae.blog.model;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;

@Setter
@Getter
@NoArgsConstructor
@Entity
public class User extends Timestamped{

    @GeneratedValue(strategy = GenerationType.AUTO)
    @Id
    private Long id;

    @Column
    private String username;

    @Column(nullable = false)
    private String password;


    @Column(nullable = false)
    @Enumerated(value = EnumType.STRING)
    private UserRole role;

    @Column(nullable = true)
    private Long kakaoId;

    //public User(String username, String password, String email, UserRole role) {
    public User(String username, String password, UserRole role) {
        this.username = username;
        this.password = password;
        //this.email = email;
        this.role = role;
        this.kakaoId = null;
    }

    //public User(String username, String password, String email, UserRole role, Long kakaoId) {
    public User(String username, String password, UserRole role, Long kakaoId) {
        this.username = username;
        this.password = password;
        //this.email = email;
        this.role = role;
        this.kakaoId = kakaoId;
    }
}
