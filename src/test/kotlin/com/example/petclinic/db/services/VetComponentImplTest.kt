package com.example.petclinic.db.services

import com.example.petclinic.db.entity.Gender
import com.example.petclinic.db.entity.Vet
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.Assertions.*
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.transaction.annotation.Transactional
import java.text.SimpleDateFormat
import java.util.*

@SpringBootTest
@Transactional
class VetComponentImplTest @Autowired constructor(val vetComponent: VetComponent) {

    @Test
    fun save() {
        val vets = vetComponent.findAll()
        assertEquals(vets.size,17)
        val id = vets.maxBy{it.id!!}.id!! + 1
        val date = Date()
        val vet = Vet(id,"owner","Address","phone",date, Gender.MALE,"first")
        val vetSave = vetComponent.save(vet)
        assertEquals(vetComponent.findAll().size,18)
        val vetFrom = vetComponent.findById(vetSave.id!!)
         assertEquals(vetFrom.gender, Gender.MALE )
        assertEquals(vetFrom.fullName,"owner")
        assertEquals(vetFrom.address,"Address")
        assertEquals(vetFrom.phone,"phone")
        assertEquals(vetFrom.qualification,"first")

        val pattern = "yyyy-MM-dd"
        val simpleDateFormat = SimpleDateFormat(pattern)
        assertEquals(vetFrom.birthday.toString(),simpleDateFormat.format(date).toString())
        vetComponent.deleteById(vetFrom.id!!)
        assertEquals(vetComponent.findAll().size, 17)
    }

    @Test
    fun findById() {
        val vet = vetComponent.findById(44)
        assertEquals(vet.id, 44)
        assertEquals(vet.gender, Gender.MALE )
        assertEquals(vet.fullName,"Russ Abbot")
        assertEquals(vet.address,"Baltimore")
        assertEquals(vet.phone,"73-35-2324")
        assertEquals(vet.birthday.toString() ,"1990-01-30")
    }

    @Test
    fun findAll() {
        assertEquals(vetComponent.findAll().size, 17)
    }

    @Test
    fun deleteById() {
    }

    @Test
    fun findByName() {
        val vetsFromTable = vetComponent.findByName("Russ")
        assertEquals(vetsFromTable.size, 1)
        val vet = vetsFromTable[0]

        assertEquals(vet.gender, Gender.MALE )
        assertEquals(vet.fullName,"Russ Abbot")
        assertEquals(vet.address,"Baltimore")
        assertEquals(vet.phone,"73-35-2324")
        assertEquals(vet.qualification, "first" )
    }

    @Test
    fun findByPhone() {
        val vet = vetComponent.findByPhone("73-35-2324").get(0)
        assertEquals(vet.gender, Gender.MALE )
        assertEquals(vet.fullName,"Russ Abbot")
        assertEquals(vet.address,"Baltimore")
        assertEquals(vet.phone,"73-35-2324")
        assertEquals(vet.qualification, "first" )
    }
}