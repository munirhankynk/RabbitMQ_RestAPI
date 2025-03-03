package com.rabbitmq.RestAPI_RabbitMQ.model;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import lombok.Data;
import lombok.RequiredArgsConstructor;

import java.time.LocalDateTime;
import java.util.UUID;

@Entity
@Data
@RequiredArgsConstructor
public class Transaction  {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private UUID id;

    private String sender;
    private String receiver;
    private double amount;
    private LocalDateTime timestamp;


}

