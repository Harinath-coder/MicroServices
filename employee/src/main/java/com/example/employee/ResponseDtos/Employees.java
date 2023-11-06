package com.example.employee.ResponseDtos;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class Employees {
    private String empId;
    private String name;
    private String emailId;
    private int mobileNum;
    private String address;

}
