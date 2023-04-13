package com.example.petclinic.transport.service

import com.example.petclinic.db.entity.Owner

import org.springframework.beans.factory.annotation.Autowired
import org.springframework.kafka.core.KafkaTemplate
import org.springframework.stereotype.Service


@Service
class ProducerService @Autowired constructor(
    val kafkaTemplate: KafkaTemplate<String, String>
) {

    fun produce(message: String) {
        println("Producing the message: $message")
        kafkaTemplate.send("messages", message)
    }
    fun produceOwner(owner: Owner){
        println("Producing the message: ${getOwnerJson(owner)}")
        println("kafkaTemplate.kafkaAdmin - "+kafkaTemplate.kafkaAdmin)
        println("kafkaTemplate.defaultTopic - "+kafkaTemplate.defaultTopic)
        println("kafkaTemplate.messageConverter - "+kafkaTemplate.messageConverter)
        println("kafkaTemplate.transactionIdPrefix - "+kafkaTemplate.transactionIdPrefix)
        println("kafkaTemplate.producerFactory - "+kafkaTemplate.producerFactory)
        println("--- - ")
        (1..100).forEach {
            kafkaTemplate.send("messages", it.toString())
        }

       // kafkaTemplate.send("messages", getOwnerJson(owner))
    }
}