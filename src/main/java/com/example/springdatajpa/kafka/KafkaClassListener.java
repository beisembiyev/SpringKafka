package com.example.springdatajpa.kafka;

import org.springframework.kafka.annotation.KafkaListener;

public class KafkaClassListener {
    @KafkaListener(topics = "mytopic", groupId = "myGroup")
    void listener(String data){
        System.out.println("received "+ data);
    }
}