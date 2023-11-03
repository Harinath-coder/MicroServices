package com.example.reportingManager.Dtos;


import lombok.Data;

@Data
public class GetLeavesUnderManagerDto {
    private String hrOrRmId;
    private String status;

    public GetLeavesUnderManagerDto(String rmId, String pending) {
        this.hrOrRmId = rmId;
        this.status = pending;
    }

    public GetLeavesUnderManagerDto() {
    }
}

