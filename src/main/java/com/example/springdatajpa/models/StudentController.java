package com.example.springdatajpa.models;

import org.apache.kafka.common.protocol.types.Field;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.concurrent.ListenableFutureAdapter;
import org.springframework.web.bind.annotation.*;

import javax.websocket.SendResult;
import java.time.LocalDate;
import java.util.List;

@RestController
@RequestMapping("api/v1/student")
public class StudentController {

    private final StudentService studentService;
    private KafkaTemplate<String, String> kafkaTemplate;
    @Autowired
    public StudentController(StudentService studentService, KafkaTemplate<String, String> kafkaTemplate) {
        this.studentService = studentService;
        this.kafkaTemplate=kafkaTemplate;
    }

    @GetMapping
    public List<Student> getStudents(){
        return studentService.getStudents();
    }

    @PostMapping
    public void createStudent(@RequestBody Student student){

        kafkaTemplate.send("mytopic", student.getName());
        studentService.createStudent(student);
    }
    @DeleteMapping(path = "{studentId}")
    public void deleteStudent(@PathVariable("studentId") Long studentId){
        studentService.deleteStudent(studentId);
    }


    @PutMapping(path = "{studentId}")
    public void editStudent(@PathVariable("studentId") Long studentId,
                            @RequestParam(required = false)String name,
                            @RequestParam(required = false)String email){
        studentService.editStudent(studentId, name,email);
    }
}
