package com.example.petclinic.db.entity

import org.junit.jupiter.api.Assertions.*
import org.junit.jupiter.api.Test

class ServiceTest(){
    @Test
    fun makeTest(){
        var service = Service(0,"price","description",1.1)
        assertEquals(service.id,0)
        assertEquals(service.name,"price")
        assertEquals(service.description,"description")
        assertEquals(service.price,1.1)
    }
}