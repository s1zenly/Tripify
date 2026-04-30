package com.startupsync.com.userservice.entity;

import jakarta.persistence.*;
import lombok.Data;

@Entity
@Data
@Table(name = "Skill")
public class Skill {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "skillname")
    private String skillName;

    @ManyToOne
    @JoinColumn(name = "userid")
    private User user;

    public Skill() {
        super();
    }
}

