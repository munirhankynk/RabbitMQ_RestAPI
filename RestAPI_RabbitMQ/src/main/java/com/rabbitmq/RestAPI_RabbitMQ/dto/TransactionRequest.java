package com.rabbitmq.RestAPI_RabbitMQ.dto;



import lombok.Data;
import lombok.RequiredArgsConstructor;

@Data
@RequiredArgsConstructor
public class TransactionRequest {


    private String sender;
    private String receiver;
    private double amount;


}
