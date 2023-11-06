package com.example.employee.Controllers;

import com.example.employee.Models.Employee;
import com.example.employee.ResponseDtos.Employees;
import com.example.employee.Services.EmpService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/emp/empSearch")
public class EmpController {

    @Autowired
    private EmpService empService;

    private Logger LOG = LoggerFactory.getLogger(EmpController.class);
    @PostMapping("/addEmp")
    @PreAuthorize("hasAnyAuthority('ROLE_ADMIN','ROLE_HR')")
    public ResponseEntity<Map<String, Object>> addEmp(@RequestBody Employee emp){

        String response =  empService.addEmp(emp);
        Map<String, Object> output = new HashMap<>();
        output.put("status" , true);
        output.put("message", response);
        LOG.info(response);
        return new ResponseEntity<>(output, HttpStatus.OK);
    }

    @GetMapping("/getEmpUnderManager")
    public List<Employees> getEmpUnderManager(@RequestParam("managerId") String managerId){
        return empService.getEmpUnderManager(managerId);
    }


    @GetMapping("/getEmpEmail")
    public String getEmployee(@RequestParam("empId") String empId){
        Employee employee = empService.getEmployee(empId);
        return employee.getEmail();
    }

    @GetMapping("/getEmployeeMail")
    public ResponseEntity<String> getEmpMail(@RequestParam("empId") String empId){
        Employee employee = empService.getEmployee(empId);
        LOG.info("employee mail -----> "+employee.getEmail());
        return new ResponseEntity<>(employee.getEmail(), HttpStatus.OK);
    }
    @GetMapping("/checkEmp")
    public ResponseEntity<?> checkEmp(@RequestParam("empId") String empId){
        Employee employee = empService.getEmployee(empId);
        if(employee != null){
            return new ResponseEntity<>("true",HttpStatus.OK);
        }
        return new ResponseEntity<>("false", HttpStatus.BAD_REQUEST);
    }


    @GetMapping("/helloEmp")
  //  @PreAuthorize("hasAuthority('ROLE_ADMIN')")
    public String helloEmp(){
        return "hello emp am your team leader";
    }

    @GetMapping("/hello")
    public String hello(){
        return "hello i am without calling role";
    }

}
