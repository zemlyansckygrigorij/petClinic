package com.example.petclinic.transport.service

import com.example.petclinic.db.entity.Owner
import com.example.petclinic.db.entity.Pet
import com.example.petclinic.db.entity.Services
import com.example.petclinic.db.entity.Vet
import com.example.petclinic.transport.mapper.OwnerMapper
import com.example.petclinic.transport.mapper.PetMapper
import com.example.petclinic.transport.mapper.ServiceMapper
import com.example.petclinic.transport.mapper.VetMapper
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.kafka.core.KafkaTemplate
import org.springframework.stereotype.Service


@Service
class ProducerService @Autowired constructor(
    val kafkaTemplate: KafkaTemplate<String, Any>
) {
    fun produceOwner(owner: Owner){
        kafkaTemplate.send("owners", OwnerMapper().getOwnerDto(owner))
    }
    fun producePet(pet: Pet){
        kafkaTemplate.send("pets", PetMapper().getPetDto(pet))
    }
    fun produceService(services: Services){
        kafkaTemplate.send("services", ServiceMapper().getServicesDto(services))
    }
    fun produceVet(vet: Vet){
        kafkaTemplate.send("vets", VetMapper().getVetDto(vet))
    }
}