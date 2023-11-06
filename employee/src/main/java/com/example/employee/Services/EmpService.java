package com.example.employee.Services;

import com.example.employee.Models.Employee;
import com.example.employee.Models.UserCredential;
import com.example.employee.Repositories.EmpRepository;
import com.example.employee.Repositories.UserCredentialRepo;
import com.example.employee.RequestDtos.CredentialDto;
import com.example.employee.ResponseDtos.Employees;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.*;

@Service
public class EmpService {
   // private static final Logger log = LoggerFactory.getLogger(EmpService.class);
    @Autowired
    private EmpRepository empRepository;

    @Autowired
    private UserCredentialRepo userCredentialRepo;

    @Autowired
    PasswordEncoder passwordEncoder;

    @Autowired
    private RestTemplate restTemplate;
    public int getRows(){
        int cnt = empRepository.findRows();
        return cnt+1;
    }
    public String addEmp(Employee emp) {
       // String empID = UUID.randomUUID().toString();
        Calendar calendar =Calendar.getInstance();
        int year = calendar.get(Calendar.YEAR);
        String empID = "EMP"+year+""+getRows();
        emp.setEmpId(empID);
        emp.setStatus("ACTIVE");

        String roles = emp.getRole();
//        String url ="http://localhost:8085/accessLevelMapping/addAccessLevelToEmp?empId="+empID+"&position="+role;
//        String response = restTemplate.getForObject(url, String.class);

        UserCredential userCredential = new UserCredential();
        userCredential.setEmpId(empID);
        userCredential.setPassword(passwordEncoder.encode(emp.getPassword()));
        userCredential.setRoles(roles);
        userCredentialRepo.save(userCredential);
        empRepository.save(emp);
       // log.info("new employee added to the database with emp id " + empID);
        return "employee with id "+empID+" added to db";
    }

    public List<Employees> getEmpUnderManager(String managerId) {
        List<Employees> employeesList = new ArrayList<>();
//        List<Employee> employeeList = empRepository.findAllByManagerId(managerId);
//        for(int i=0; i<employeeList.size(); i++){
//            Employee employee = employeeList.get(i);
//            Employees employees = Employees.builder()
//                    .empId(employee.getEmpId())
//                    .address(employee.getAddress())
//                    .name(employee.getName())
//                    .emailId(employee.getEmpId())
//                    .mobileNum(employee.getMobileNum())
//                    .build();
//            employeesList.add(employees);
//
//        }
        return employeesList;
    }

    public Employee getEmployee(String empId) {
        Optional<Employee> employee = empRepository.findByEmpId(empId);
        if(employee.isPresent()){
            return employee.get();
        }
        return null;
    }
}