package com.example.employee.Controllers;

import com.example.employee.Models.Employee;
import com.example.employee.RequestDtos.EmpRatingDto;
import com.example.employee.Services.EmpRatingsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/emp/empRatings")
public class EmpRatingsController {
    @Autowired
    private EmpRatingsService empRatingsService;

    @PostMapping("addRatings")
    @PreAuthorize("hasAnyAuthority('ROLE_MANAGER','ROLE_HR')")
    public ResponseEntity<Map<String, Object>> addRatings(@RequestBody EmpRatingDto empRatingDto) {
        Map<String,Object> response = new HashMap<>();
        try {
            String message = empRatingsService.addRatings(empRatingDto);
            response.put("status", true);
            response.put("message", message);
            return new ResponseEntity<>(response, HttpStatus.OK);
        }catch (Exception e){
            response.put("status", false);
            response.put("message", e.getMessage());
            return new ResponseEntity<>(response, HttpStatus.BAD_REQUEST);
        }
    }

    @GetMapping("getRatingsLess")
    public void getRaingsLess(){
        empRatingsService.getRatingsLess();
    }
}
