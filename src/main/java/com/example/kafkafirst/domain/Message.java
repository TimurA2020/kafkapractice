package com.example.kafkafirst.domain;

import lombok.Getter;
import lombok.Setter;

import java.time.LocalDate;

@Getter
@Setter
public class Message {

    private String text;
    private LocalDate date;
}
