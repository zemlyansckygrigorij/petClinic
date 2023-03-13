package com.example.petclinic.db.entity

import jakarta.persistence.*
import org.junit.jupiter.api.Assertions.*
import org.junit.jupiter.api.Test
import java.util.*

/**
 * @author Grigoriy Zemlyanskiy
 * @version 1.0
 * class VetTest
 */
class VetTest(){
    @Test
    fun makeTest(){
        var date = Date()
        var vet = Vet(0,
            "fullName",
            "address",
            "phone",
            date,
            Gender.MALE,
            "qualification")
        assertEquals(vet.id,0)
        assertEquals(vet.fullName,"fullName")
        assertEquals(vet.address,"address")
        assertEquals(vet.phone,"phone")
        assertEquals(vet.birthday, date)
        assertEquals(vet.gender,Gender.MALE)
    }
}
