package com.example.kafkafirst.controllers;

import com.example.kafkafirst.domain.Message;
import lombok.RequiredArgsConstructor;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping("/chat")
@RequiredArgsConstructor
public class KafkaController {

    private final KafkaTemplate<String, String> kafkaTemplate;

    private List<Message> consumedMessages = new ArrayList<>(); // Store consumed messages here

    @GetMapping("/messages")
    public List<Message> getConsumedMessages() {
        return consumedMessages;
    }


    @GetMapping
    @KafkaListener(topics = "main-chat", groupId = "chat-application")
    public Message getMessage(String message) {
        Message text = new Message();
        text.setText(message);
        text.setDate(LocalDate.now());
        System.out.println(message);
        return text;
    }

    @PostMapping("/send")
    @KafkaListener(topics = "main-chat", groupId = "chat-application")
    public void sendMessage(@RequestParam String message) {
        kafkaTemplate.send("main-chat", message);
    }

    @KafkaListener(topics = "main-chat", groupId = "chat-application")
    public void receiveMessage(String message) {
        System.out.println("Received message: " + message);

        Message processedMessage = new Message();
        processedMessage.setText(message);
        processedMessage.setDate(LocalDate.now());

        consumedMessages.add(processedMessage);
        System.out.println(consumedMessages.toString());
    }
}
