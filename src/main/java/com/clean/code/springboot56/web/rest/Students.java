package com.clean.code.springboot56.web.rest;

import com.clean.code.springboot56.model.Student;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping("/api")
public class Students {
    @GetMapping("/students")
    public ResponseEntity getAll(){
        Student student = new Student(1,"Aziz", "Hakimov", "3-kurs");
        Student student1 = new Student(2,"Hakim", "Azizov", "5-kurs");
        Student student2 = new Student(3,"Mazzi", "Do'shimov", "3-kurs");
        Student student3 = new Student(4,"Yonboshbergan", "Do'shimov", "2-kurs");

        List<Student> students = new ArrayList<>();
        students.add(student);
        students.add(student1);
        students.add(student2);
        students.add(student3);
        return ResponseEntity.ok(students);
    }

    @GetMapping("/students/{id}")
    public  ResponseEntity getOne(@PathVariable int id){
        Student student3 = new Student(id,"Yonboshbergan", "Do'shimov", "2-kurs");
        return ResponseEntity.ok(student3);

    }

    @PostMapping ("/students")
    public ResponseEntity create (@RequestBody Student student){
        return  ResponseEntity.ok(student);
    }
    @PutMapping ("/students/{id}")
    public ResponseEntity update(@PathVariable int id,
            @RequestBody Student studentnew){
        Student student = new Student(1,"Zoirjon", "Abdullayev", "3-kurs");
        student.setId(id);
        student.setName(studentnew.getName());
        student.setLast_name((studentnew.getLast_name()));
        return ResponseEntity.ok(student);
    }

}