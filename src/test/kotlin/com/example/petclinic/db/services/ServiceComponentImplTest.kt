package com.example.petclinic.db.services

import com.example.petclinic.db.entity.Services
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.Assertions.*
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.context.SpringBootTest

@SpringBootTest
class ServiceComponentImplTest @Autowired constructor( val serviceComponent: ServiceComponent){

    @Test
    fun save() {
        val services = serviceComponent.findAll()
        assertEquals(services.size,11)
        val id = services.maxBy{it.id}.id + 1
        val service = Services(id,"name","description",1.9)
        val serviceSave = serviceComponent.save(service)
        assertEquals(serviceComponent.findAll().size,12)
        val serviceFrom = serviceComponent.findById(serviceSave.id)
        assertEquals(serviceFrom.id, serviceSave.id)
        assertEquals(serviceFrom.name, serviceSave.name)
        assertEquals(serviceFrom.description, serviceSave.description)
        assertEquals(serviceFrom.price, serviceSave.price)
        serviceComponent.deleteById(serviceSave.id)
        assertEquals(services.size,11)
    }

    @Test
    fun findById() {
        val serviceFrom = serviceComponent.findById(1)
        assertEquals(serviceFrom.id, 1)
        assertEquals(serviceFrom.name, "Pet Wellness Exams")
        assertEquals(serviceFrom.price, 10.1)
    }

    @Test
    fun findAll() {
        assertEquals(serviceComponent.findAll().size,11)
    }

    @Test
    fun deleteById() {
    }

    @Test
    fun findByName() {
        val services = serviceComponent.findByName("Pet")
        assertEquals(services.size,6)
        val service = services[0]
        assertEquals(service.id, 1)
        assertEquals(service.name, "Pet Wellness Exams")
        assertEquals(service.price, 10.1)
    }

}