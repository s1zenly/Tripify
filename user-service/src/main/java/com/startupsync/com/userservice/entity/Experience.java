package com.startupsync.com.userservice.entity;

import jakarta.persistence.*;
import lombok.Data;

import java.util.Date;

@Entity
@Data
@Table(name = "Experience")
public class Experience {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "companylogo")
    private String companyLogo;

    @Column(name = "designation")
    private String designation;

    @Column(name = "company")
    private String company;

    @Column(name = "fromdate")
    private Date fromDate;

    @Column(name = "todate")
    private Date toDate;

    @Column(name = "description", columnDefinition = "TEXT")
    private String description;

    @ManyToOne
    @JoinColumn(name = "userid")
    private User user;

    public Experience() {
        super();
    }
}
