package com.example.springdatajpa.models;

import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.time.LocalDate;
import java.util.List;

@Configuration
public class StudentConfig {
    @Bean
    CommandLineRunner commandLineRunner(StudentRepository studentRepository){
        return args -> {
            Student f=new Student(
                    "first",
                    "email",
                    LocalDate.of(1990,12,9)
            );
            Student s=new Student(
                    "second",
                    "email",
                    LocalDate.of(1997,2,8)
            );
            studentRepository.saveAll(List.of(f,s));
        };
    }
}
