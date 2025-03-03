package com.rabbitmq.RestAPI_RabbitMQ.listener;

import com.rabbitmq.RestAPI_RabbitMQ.dto.TransactionRequest;
import com.rabbitmq.RestAPI_RabbitMQ.model.Transaction;
import com.rabbitmq.RestAPI_RabbitMQ.repository.TransactionRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Component;
import java.time.LocalDateTime;


@Component
@RequiredArgsConstructor
public class TransactionListener {
    private final TransactionRepository transactionRepository;


    @RabbitListener(queues = "transactionQueue")
    public void handleTransaction(TransactionRequest request) {

        Transaction transaction = new Transaction();
        transaction.setSender(request.getSender());
        transaction.setReceiver(request.getReceiver());
        transaction.setAmount(request.getAmount());
        transaction.setTimestamp(LocalDateTime.now());
        transactionRepository.save(transaction);

        System.out.println("Transaction saved: " + request);
    }
}
