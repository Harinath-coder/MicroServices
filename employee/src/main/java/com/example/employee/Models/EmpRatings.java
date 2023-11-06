package com.example.employee.Models;

import jakarta.persistence.*;
import lombok.Data;

@Entity
@Table
@Data
public class EmpRatings {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;
    private int month;
    private int year;
    private String technology;

    private Double technicalRating;

    private Double communicationRating;

    private String remarks;

    private Boolean remainder;

    @JoinColumn
    @ManyToOne
    private Employee employee;
}
