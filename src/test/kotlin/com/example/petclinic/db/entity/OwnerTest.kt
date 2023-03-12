package com.example.petclinic.db.entity

import org.junit.jupiter.api.Assertions.*
import org.junit.jupiter.api.Test
import java.util.*

class OwnerTest(){
    @Test
    fun makeTest(){
        var date = Date()

        var owner = Owner(0L,"owner","Address","phone",date, Gender.MALE)
        assertEquals(owner.id,0)
        assertEquals(owner.gender,Gender.MALE.toString())
        assertEquals(owner.fullName,"owner")
        assertEquals(owner.address,"Address")
        assertEquals(owner.phone,"phone")
        assertEquals(owner.birthday,date)
    }
}