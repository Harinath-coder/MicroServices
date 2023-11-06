package com.example.employee.RequestDtos;

import lombok.Data;

@Data
public class EmpRatingDto {
    private String empid;
    private int month;
    private int year;
    private String technology;
    private Double technicalRating;
    private Double communicationRating;
    private String remarks;
}
