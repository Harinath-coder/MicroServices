package com.example.employee.Controllers;

import com.example.employee.CustumExceptions.InvalidEmpId;
import com.example.employee.Repositories.EmpSkillRepo;
import com.example.employee.Services.EmpSkillService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.Map;

@RestController
@RequestMapping("/api/emp/empSkill")
public class EmpSkillController {

    @Autowired
    private EmpSkillService empSkillService;

    @PostMapping("/addPosition")
    @PreAuthorize("hasAuthority('ROLE_HR')")
    public ResponseEntity<Map<String, Object>> addPosition(@RequestParam("empId") String empid, @RequestParam("position") String position, @RequestParam("skill") String skill){
        Map<String, Object> response = new HashMap<>();
        try {
            String message = empSkillService.addPosition(empid, position, skill);
            response.put("status",true);
            response.put("message", message);
            return new ResponseEntity<>(response, HttpStatus.OK);
        }catch (InvalidEmpId e){
            response.put("status",false);
            response.put("message",e.getMessage());
            return new ResponseEntity<>(response,HttpStatus.BAD_REQUEST);
        }
    }
}