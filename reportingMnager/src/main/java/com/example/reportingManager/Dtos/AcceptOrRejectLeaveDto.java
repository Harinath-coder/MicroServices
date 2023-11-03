package com.example.reportingManager.Dtos;


import lombok.Data;

@Data
public class AcceptOrRejectLeaveDto {
    private String empId;
    private String hrOrRmId;
    private String status;
}
