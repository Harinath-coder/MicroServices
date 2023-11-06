package com.example.employee.Services;

import com.example.employee.CustumExceptions.InvalidEmpId;
import com.example.employee.Models.EmpSkill;
import com.example.employee.Models.Employee;
import com.example.employee.Repositories.EmpRepository;
import com.example.employee.Repositories.EmpSkillRepo;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class EmpSkillService {

    private static final Logger log= LoggerFactory.getLogger(EmpService.class);
    @Autowired
    private EmpSkillRepo empSkillRepo;

    @Autowired
    private EmpRepository empRepository;
    public String addPosition(String empid, String position, String skill) throws InvalidEmpId {

        Optional<Employee> employeeOptional = empRepository.findByEmpId(empid);
        if(employeeOptional.isEmpty()){
            log.info("given emp id "+empid+" is not valid or not registered with databses");
            throw new InvalidEmpId("given id not registred with data base");
        }
        Employee employee = employeeOptional.get();

        EmpSkill empSkill = new EmpSkill();
        empSkill.setSkill(skill);
        empSkill.setPosition(position);
        empSkill.setEndingDay("NA");
        empSkill.setEmployee(employee);

       // employee.getEmpSkills().add(empSkill);
        employee.setEmpSkill(empSkill);
        //empSkillRepo.save(empSkill);
        empRepository.save(employee);
        log.info("new "+position+" added -> emp id"+empid);
        return "added position";
    }
}