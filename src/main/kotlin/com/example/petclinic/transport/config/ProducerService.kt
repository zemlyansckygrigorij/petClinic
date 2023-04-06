package com.example.petclinic.transport.config

import org.springframework.beans.factory.annotation.Autowired
import org.springframework.kafka.core.KafkaTemplate
import org.springframework.stereotype.Service


@Service
class ProducerService @Autowired constructor(
    val kafkaTemplate: KafkaTemplate<String, String>
) {

    fun produce(message: String) {
        println("Producing the message: $message")
        kafkaTemplate!!.send("messages", message)
    }
}