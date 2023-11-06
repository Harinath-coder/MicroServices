package com.example.employee.Models;

import com.fasterxml.jackson.annotation.JsonFormat;
import jakarta.persistence.*;
import lombok.Data;
import org.hibernate.annotations.CreationTimestamp;

import java.time.LocalDate;
import java.util.Date;

@Entity
@Table(name = "empSkill")
@Data
public class EmpSkill {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;
    private String skill;
    private String position;
    @CreationTimestamp
    private Date startingDay;
    private String endingDay;

    @JoinColumn
    @OneToOne
    private Employee employee;
}