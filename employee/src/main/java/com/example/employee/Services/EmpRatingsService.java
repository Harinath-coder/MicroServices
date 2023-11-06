package com.example.employee.Services;

import com.example.employee.CustumExceptions.InvalidEmpId;
import com.example.employee.Models.EmpRatings;
import com.example.employee.Models.EmpSkill;
import com.example.employee.Models.Employee;
import com.example.employee.Repositories.EmpRatingsRepository;
import com.example.employee.Repositories.EmpRepository;
import com.example.employee.Repositories.EmpSkillRepo;
import com.example.employee.RequestDtos.EmpRatingDto;
import lombok.ToString;
import lombok.Value;
import org.slf4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;

import org.slf4j.LoggerFactory;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Optional;

@Service
@Component

public class EmpRatingsService {
   // private static final Logger log = LoggerFactory.getLogger(EmpRatingsService.class);
    @Autowired
    private EmpRatingsRepository empRatingsRepository;
    @Autowired
    private EmpRepository empRepository;

    @Autowired
    private EmpSkillRepo empSkillRepo;

    @Autowired
    JavaMailSender mailSender;

    public void getRatingsLess() {
        List<EmpRatings> empRatingsList = empRatingsRepository.findAll();

        for (int i = 0; i < empRatingsList.size(); i++) {
            EmpRatings empRatings = empRatingsList.get(i);
            if (empRatings.getTechnicalRating() < 2.5 || empRatings.getCommunicationRating() < 2.5) {
                Employee employee = empRatings.getEmployee();
                System.out.println(employee.getEmail() + "notified about rating");
            }
        }
        // return new ArrayList<>();
    }

    public String addRatings(EmpRatingDto empRatingDto) throws InvalidEmpId{
        Optional<Employee> employeeOptional = empRepository.findByEmpId(empRatingDto.getEmpid());
        if(employeeOptional.isEmpty()){
           // log.info("given emp id not registered with database -> "+empRatingDto.getEmpid());
            throw new InvalidEmpId("invalid credentials of employee");
        }
        Employee employee = employeeOptional.get();

        EmpRatings empRatings = new EmpRatings();

        empRatings.setCommunicationRating(empRatingDto.getCommunicationRating());
        empRatings.setTechnicalRating(empRatingDto.getTechnicalRating());
        empRatings.setMonth(empRatingDto.getMonth());
        empRatings.setYear(empRatingDto.getYear());
        empRatings.setRemarks(empRatingDto.getRemarks());
        empRatings.setTechnology(empRatingDto.getTechnology());
        empRatings.setEmployee(employee);
        employee.getEmpRatingsList().add(empRatings);
        // empRatingsRepository.save(empRatings);
        empRepository.save(employee);


        System.out.println("ratings added");
       // log.info("employee ratings added for emp id "+empRatingDto.getEmpid());
        return "added rating";
    }

 //   @Scheduled(cron = "*/5 * * * * *")
    @Scheduled(cron = "0 0 17 1-10 * ?")
    public void schedulingTask() throws InterruptedException {
        //cron = "0 0 17 1-10 * ?" -> every month 1 to 10 date 5 pm
        System.out.println("sheduling task for every 30 sec");

        Date date = new Date();
        // System.out.println(date+" -> month num" + date.getMonth());
        int monthNum = Integer.valueOf(date.getMonth());
        monthNum = monthNum == 0 ? 11 : (monthNum - 1);

        List<EmpRatings> empRatingsList = empRatingsRepository.findByMonth(monthNum);
        for (int i = 0; i < empRatingsList.size(); i++) {
            //  System.out.println(empRatingsList.get(i).getCommunicationRating());
            Double technicalRating = empRatingsList.get(i).getTechnicalRating();
            Double communicationRating = empRatingsList.get(i).getCommunicationRating();

            if (technicalRating < 2.5 || communicationRating < 2.5) {
                EmpRatings empRatings = empRatingsList.get(i);
                Employee employee = empRatings.getEmployee();
                //  System.out.println(employee.getName());

                String body = "Dear " + employee.getName() + " !!!," + "\n" +
                        "I hope this message finds you well." + "\n" +
                        "We value your contributions ansd dedication to Eidiko Systems Integrators pvt ltd, and we" + "\n" +
                        "wanted to take a moment to address recent performance observations. " + "\n" +
                        "We've noticed slight decrease in performence, and while everyone faces challenges from time to time " + "\n" +
                        "we want to ensure you have the support you need to succeed." + "\n" +
                        "your technical ability ratings " + technicalRating + "\n" +
                        "your communication ability ratings " + communicationRating + "\n" +
                        "Thank you fro your continued dedicaion to Eidiko. We look forward to working " + "\n" +
                        "together to ensure your success." + "\n" +
                        "Warm regards," + "\n" +
                        "Eidiko Systems Integrators.";

//                SimpleMailMessage mailMessage = new SimpleMailMessage();
//
//                mailMessage.setFrom("");
//                mailMessage.setTo(employee.getEmail());
//                mailMessage.setSubject("Performance Report of previous month");
//                mailMessage.setText(body);
//
//                mailSender.send(mailMessage);


                String employeeName = employee.getName();
                String position = employee.getEmpSkill().getPosition();
                Boolean remainder = empRatings.getRemainder();
                //   System.out.println(remainder);

                if (position.equals("Training") && remainder == null) {
                    empRatings.setRemainder(true);
                    empRatingsRepository.save(empRatings);
                    System.out.println(employeeName + " ->" + position);

                    if (technicalRating < 2.5) {
                        System.out.println("your score is less in technical " + technicalRating);
                    } else if (communicationRating < 2.5) {
                        System.out.println("your score is less in communication" + communicationRating);
                    } else {
                        System.out.println("your score is less in both " + technicalRating + "->" + communicationRating);
                    }
                  // log.info("employee reminded about his performence id is "+employee.getEmpId());
                }
            }
        }
    }
}