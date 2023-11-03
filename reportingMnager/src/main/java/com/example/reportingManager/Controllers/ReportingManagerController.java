package com.example.reportingManager.Controllers;


import com.example.reportingManager.Dtos.AcceptOrRejectLeaveDto;
import com.example.reportingManager.Dtos.AssignManagerDto;
import com.example.reportingManager.Dtos.GetLeavesUnderManagerDto;
import com.example.reportingManager.Models.Leaves;
import com.example.reportingManager.Services.ReportingManagerService;
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
@RequestMapping("/api/rm")
public class ReportingManagerController {

    @Autowired
    private ReportingManagerService reportingManagerService;

    private Logger LOG = LoggerFactory.getLogger(ReportingManagerController.class);

    @PostMapping("/assignManager")
    @PreAuthorize("hasAnyAuthority('ROLE_HR','ROLE_ADMIN','ROLE_SUPER ADMIN')")
    public ResponseEntity<Map<String, Object>> assignManager(@RequestBody AssignManagerDto assignManagerDto) {
        String message = reportingManagerService.assignManager(assignManagerDto);
        Map<String, Object> response = new HashMap<>();
        response.put("status", true);
        response.put("message", message);
        LOG.info(message);
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    @PostMapping("/hello")
    public void hello(@RequestParam("message") String message) {
        System.out.println(message);
    }

    @GetMapping("/hi")
    public String hi() {
        return "hi";
    }

    @GetMapping("/getEmpLeavesUnderManager")
    @PreAuthorize("hasAuthority('ROLE_MANAGER')")
    public ResponseEntity<Map<String, Object>> getEmpLeavesUnderManager(@RequestBody GetLeavesUnderManagerDto getLeavesUnderManagerDto) {
        //System.out.println("starting");
        Map<String, Object> response = new HashMap<>();
        try {
            //System.out.println("finding");
            List<Leaves> output = reportingManagerService.getEmpLeavesUnderManager(getLeavesUnderManagerDto);
            response.put("status", true);
            response.put("message", "getting leaves of employees under given manager");
            response.put("data", output);
            LOG.info("getting leaves of employees under manager "+getLeavesUnderManagerDto.getHrOrRmId()+ " is success");
            return new ResponseEntity<>(response, HttpStatus.OK);
        } catch (Exception e) {
            System.out.println(e.getMessage());
            response.put("status", false);
            response.put("message", e.getMessage());
            LOG.error(e.getMessage());
            return new ResponseEntity<>(response, HttpStatus.NOT_FOUND);
        }
    }

    @PostMapping("/acceptOrRejectLeave")
    @PreAuthorize("hasAuthority('ROLE_MANAGER')")
    public ResponseEntity<Map<String, Object>> acceptLeave(@RequestBody AcceptOrRejectLeaveDto acceptLeave) {
        Map<String, Object> response = new HashMap<>();
        try {
            String message = reportingManagerService.acceptOrRejectLeave(acceptLeave);
            response.put("status", true);
            response.put("message", message);
            LOG.info("leaves of empId"+acceptLeave.getEmpId()+" is "+message+" by "+acceptLeave.getHrOrRmId());
            return new ResponseEntity<>(response, HttpStatus.OK);
        } catch (Exception e) {
            response.put("status", false);
            response.put("message", e.getMessage());
            LOG.error(e.getMessage());
            return new ResponseEntity<>(response, HttpStatus.BAD_REQUEST);
        }

    }

    @GetMapping("/getManagerMailOfEmp")
    public ResponseEntity<String> getManagerMailOfEmployee(@RequestParam("empId") String empId){
        String managerMail = reportingManagerService.getManagerMailOfEmployee(empId);
        return new ResponseEntity<>(managerMail,HttpStatus.OK);
    }
}

