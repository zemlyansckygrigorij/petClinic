package com.example.petclinic.transport.service

import com.example.petclinic.db.entity.Owner
import com.example.petclinic.transport.mapper.OwnerMapper

import org.springframework.beans.factory.annotation.Autowired
import org.springframework.kafka.core.KafkaTemplate
import org.springframework.stereotype.Service


@Service
class ProducerService @Autowired constructor(
    val kafkaTemplate: KafkaTemplate<String, Owner>
) {

    fun produce(message: String) {
        println("Producing the message: $message")
        //kafkaTemplate.send("messages", message)
    }
    fun produceOwner(owner: Owner){

        println("OwnerMapper().getOwnerDto(owner) - "+OwnerMapper().getOwnerDto(owner))

        println("--- - ")
       /* (1..100).forEach {
            kafkaTemplate.send("messages", it.toString())
        }*/
       // OwnerMapper().getOwnerDto(owner)
        kafkaTemplate.send("messages", owner)
    }
}