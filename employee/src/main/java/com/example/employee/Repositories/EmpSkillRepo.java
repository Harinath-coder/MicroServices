package com.example.employee.Repositories;

import com.example.employee.Models.EmpSkill;
import jakarta.persistence.criteria.CriteriaBuilder;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface EmpSkillRepo extends JpaRepository<EmpSkill, Integer> {
}