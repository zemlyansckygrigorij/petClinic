package com.example.petclinic.db.entity

import org.junit.jupiter.api.Assertions.*
import org.junit.jupiter.api.Test

/**
 * @author Grigoriy Zemlyanskiy
 * @version 1.0
 * class PetTest
 */
class PetTest(){
    @Test
    fun makeTest(){
        var pet =Pet(0L,"Cat","Mursik",1)
        assertEquals(pet.id,0)
        assertEquals(pet.kind,"Cat")
        assertEquals(pet.name,"Mursik")
        assertEquals(pet.age,1)
    }
}