package com.rabbitmq.RestAPI_RabbitMQ.dto;

import lombok.Data;

@Data
public class LogMessageDTO {
    private String eventType;
    private String eventDetails;
}
