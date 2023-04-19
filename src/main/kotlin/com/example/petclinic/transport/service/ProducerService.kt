package com.example.petclinic.transport.service

import com.example.petclinic.db.entity.Owner
import com.example.petclinic.transport.dto.OwnerDto
import com.example.petclinic.transport.mapper.OwnerMapper

import org.springframework.beans.factory.annotation.Autowired
import org.springframework.kafka.core.KafkaTemplate
import org.springframework.stereotype.Service


@Service
class ProducerService @Autowired constructor(
    val kafkaTemplate: KafkaTemplate<String, OwnerDto>
) {
    fun produceOwner(owner: Owner){
        kafkaTemplate.send("messages", OwnerMapper().getOwnerDto(owner))
    }
}