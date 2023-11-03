package com.example.employee.Repositories;

import com.example.employee.Models.UserCredential;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserCredentialRepo extends JpaRepository<UserCredential,Integer> {
}
