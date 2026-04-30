package com.startupsync.com.userservice.entity;

import jakarta.persistence.*;
import lombok.Data;

import java.util.Date;

@Entity
@Data
@Table(name = "Education")
public class Education {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "universitylogo")
    private String universityLogo;

    @Column(name = "universityname")
    private String universityName;

    @Column(name = "degree")
    private String degree;

    @Column(name = "fromdate")
    private Date fromDate;

    @Column(name = "todate")
    private Date toDate;

    @ManyToOne
    @JoinColumn(name = "userid")
    private User user;

    public Education() {
        super();
    }
}

