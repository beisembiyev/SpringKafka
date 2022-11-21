package com.example.springdatajpa.models;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.util.List;
import java.util.Objects;
import java.util.Optional;

@Service
public class StudentService {

    private final StudentRepository studentRepository;
    @Autowired
    public StudentService(StudentRepository studentRepository) {
        this.studentRepository = studentRepository;
    }

    public List<Student> getStudents(){
        return studentRepository.findAll();
    }

    public void createStudent(Student student){
        Optional<Student> studentEmail= studentRepository.findStudentByEmail(student.getEmail());
        if (studentEmail.isPresent()){
            throw new IllegalStateException("Email is taken");
        }
        studentRepository.save(student);

    }

    @Transactional
    public void deleteStudent(Long studentId) {
        boolean exists=studentRepository.existsById(studentId);
        if (!exists){
            throw new IllegalStateException("Student with id "+studentId +" doesn't exists");
        }
        studentRepository.deleteById(studentId);
    }

    public void editStudent(Long studentId, String name, String email) {
        Student s=studentRepository.findById(studentId).
                orElseThrow(()->new IllegalStateException("Student with id "+studentId +" doesn't exists"));

        if(name!=null &&name.length()>0
                &&!Objects.equals(s.getName(),name)) {
            s.setName(name);
        }
        if(email!=null &&email.length()>0
                &&!Objects.equals(s.getEmail(),email)) {
            Optional<Student> studentEmail= studentRepository.findStudentByEmail(email);
            if (studentEmail.isPresent()){
                throw new IllegalStateException("Email is taken");
            }
            s.setEmail(email);
        }

        studentRepository.save(s);

    }
}
