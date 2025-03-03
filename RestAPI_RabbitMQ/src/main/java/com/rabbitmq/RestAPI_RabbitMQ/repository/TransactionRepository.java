package com.rabbitmq.RestAPI_RabbitMQ.repository;

import com.rabbitmq.RestAPI_RabbitMQ.model.Transaction;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.UUID;

public interface TransactionRepository extends JpaRepository<Transaction, UUID> {
}

