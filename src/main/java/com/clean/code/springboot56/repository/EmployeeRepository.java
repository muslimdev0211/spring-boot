package com.clean.code.springboot56.repository;

import com.clean.code.springboot56.domain.Employee;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface EmployeeRepository extends JpaRepository<Employee, Long> {


    List<Employee> findByname(String name);

    @Query("select e from Employee e where e.name = :name")
    List <Employee> findByQuery(@Param("name") String name);
}
