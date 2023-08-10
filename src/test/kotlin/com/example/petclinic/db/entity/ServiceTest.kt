package com.example.petclinic.db.entity

import org.junit.jupiter.api.Assertions.*
import org.junit.jupiter.api.Test
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.transaction.annotation.Transactional

/**
 * @author Grigoriy Zemlyanskiy
 * @version 1.0
 * class ServiceTest
 */
@SpringBootTest
@Transactional
class ServiceTest(){
    @Test
    fun makeTest(){
        var service = Services(0,"price","description",1.1)
        assertEquals(service.id,0)
        assertEquals(service.name,"price")
        assertEquals(service.description,"description")
        assertEquals(service.price,1.1)
    }
}