package com.example.petclinic.db.entity

import org.junit.jupiter.api.Assertions.*
import org.junit.jupiter.api.Test
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.transaction.annotation.Transactional
import java.util.*

/**
 * @author Grigoriy Zemlyanskiy
 * @version 1.0
 * class OwnerTest
 */
@SpringBootTest
@Transactional
class OwnerTest(){
    @Test
    fun makeTest(){
        var date = Date()

        var owner = Owner(0L,"owner","Address","phone",date, Gender.MALE)
        assertEquals(owner.id,0)
        assertEquals(owner.gender,Gender.MALE)
        assertEquals(owner.fullName,"owner")
        assertEquals(owner.address,"Address")
        assertEquals(owner.phone,"phone")
        assertEquals(owner.birthday,date)
    }
}