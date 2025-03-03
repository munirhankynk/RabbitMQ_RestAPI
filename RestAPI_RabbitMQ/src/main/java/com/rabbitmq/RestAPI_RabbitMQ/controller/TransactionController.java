package com.rabbitmq.RestAPI_RabbitMQ.controller;


import com.rabbitmq.RestAPI_RabbitMQ.dto.TransactionRequest;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/transaction")
public class TransactionController {

    private final RabbitTemplate rabbitTemplate;

    public TransactionController(RabbitTemplate rabbitTemplate) {
        this.rabbitTemplate = rabbitTemplate;
    }

    @PostMapping("/send")
    public ResponseEntity<String> sendTransaction(@RequestBody TransactionRequest request) {
        try {
            rabbitTemplate.convertAndSend("transactionQueue", request);
            return ResponseEntity.ok("Transaction request sent to queue.");
        }catch (Exception e) {
            return ResponseEntity.ok(e.getMessage());
        }
    }
}

