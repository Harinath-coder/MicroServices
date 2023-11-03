package com.example.reportingManager.Dtos;


import lombok.Data;

@Data
public class AssignManagerDto {

    private String empId;
    private String managerId;

    private String working;

    private String skills;
    private String startDate;

    private String endDate;

    private String createdBy;
}

