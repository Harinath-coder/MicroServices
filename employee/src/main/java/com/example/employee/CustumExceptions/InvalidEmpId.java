package com.example.employee.CustumExceptions;

public class InvalidEmpId extends Exception{
    public InvalidEmpId(String message){
        super(message);
    }
}
