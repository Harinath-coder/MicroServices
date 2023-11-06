package com.example.employee.Models;

import jakarta.persistence.*;
import lombok.Data;

import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "employee")
@Data
public class Employee {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;
    private String empId;
    private String name;
    private String email;
    private  String password;
    private int mobileNum;
    private String address;
    private String status;

   // private String managerId;
    private String role;

    @OneToMany(mappedBy = "employee", cascade = CascadeType.ALL)
    private List<EmpRatings> empRatingsList = new ArrayList<>();

    @OneToOne(mappedBy = "employee", cascade = CascadeType.ALL)
    private EmpSkill empSkill;

//    @OneToOne(mappedBy = "employee", cascade = CascadeType.ALL)
//    private UserCredential userCredential;
}
