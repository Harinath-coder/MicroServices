package com.example.employee.Repositories;

import com.example.employee.Models.EmpRatings;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface EmpRatingsRepository extends JpaRepository<EmpRatings,Integer> {
    List<EmpRatings> findByMonth(int monthNum);
    //  Optional<List<EmpRatings>> findAllByMonth(int month);
}