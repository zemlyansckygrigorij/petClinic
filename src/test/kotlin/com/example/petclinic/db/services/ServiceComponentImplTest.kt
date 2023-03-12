package com.example.petclinic.db.services

import org.junit.jupiter.api.Test

import org.junit.jupiter.api.Assertions.*
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest

@DataJpaTest
@AutoConfigureTestDatabase(replace= AutoConfigureTestDatabase.Replace.NONE)
class ServiceComponentImplTest {

    @Test
    fun save() {
    }

    @Test
    fun findById() {
    }

    @Test
    fun findAll() {
    }

    @Test
    fun deleteById() {
    }

    @Test
    fun findByName() {
    }

    @Test
    fun getServiceRepo() {
    }
}