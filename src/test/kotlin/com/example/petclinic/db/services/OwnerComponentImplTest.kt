package com.example.petclinic.db.services

import com.example.petclinic.db.entity.Gender
import com.example.petclinic.db.entity.Owner
import org.junit.Ignore
import org.junit.jupiter.api.Assertions.*
import org.junit.jupiter.api.Disabled
import org.junit.jupiter.api.Test
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.transaction.annotation.Transactional
import java.text.SimpleDateFormat
import java.util.*
import java.util.stream.Collectors


@SpringBootTest
@Transactional
class OwnerComponentImplTest @Autowired constructor(val  ownerComponent: OwnerComponent){

    @Test
    fun save() {
        val owners =ownerComponent.findAll()
        val id = owners.maxBy { it.id!! }.id!!+1
        assertEquals(owners.size, 17)
        val date = Date()
        val owner = Owner(id,"owner","Address","phone",date, Gender.MALE)
        val ownerSave = ownerComponent.save(owner)
        val ownerFromTable = ownerComponent.findById(ownerSave.id!!)
        assertEquals(ownerComponent.findAll().size, 18)
        assertEquals(ownerFromTable.gender, Gender.MALE )
        assertEquals(ownerFromTable.fullName,"owner")
        assertEquals(ownerFromTable.address,"Address")
        assertEquals(ownerFromTable.phone,"phone")

        val pattern = "yyyy-MM-dd"
        val simpleDateFormat = SimpleDateFormat(pattern)
        assertEquals(ownerFromTable.birthday.toString(),simpleDateFormat.format(date).toString())
        ownerComponent.deleteById(ownerFromTable.id!!)
        assertEquals(ownerComponent.findAll().size, 17)
    }

    @Test
    fun findById() {
        val owner = ownerComponent.findById(1)
        assertEquals(owner.id, 1)
        assertEquals(owner.gender, Gender.MALE )
        assertEquals(owner.fullName,"Bradley Alexander Abbe")
        assertEquals(owner.address,"Baltimore")
        assertEquals(owner.phone,"23-35-2324")
        assertEquals(owner.birthday.toString() ,"1990-01-30")
    }

    @Test
    fun findAll() {
        val owners =ownerComponent.findAll()
        assertEquals(owners.size, 17)
    }

    @Test
    fun deleteById() {
        val owners =ownerComponent.findAll()
        val id = owners.maxBy { it.id!! }.id!!+1
        assertEquals(owners.size, 17)
        val date = Date()
        val owner = Owner(id,"owner","Address","phone",date, Gender.MALE)
        val ownerSave = ownerComponent.save(owner)
        assertEquals(ownerComponent.findAll().size, 18)
        ownerComponent.deleteById(ownerSave.id!!)
        assertEquals(ownerComponent.findAll().size, 17)
    }

    @Test
    fun findByName() {
        val ownersFromTable = ownerComponent.findByName("Bradley")
        assertEquals(ownersFromTable.size, 1)
        val owner = ownersFromTable[0]
        assertEquals(owner.gender, Gender.MALE )
        assertEquals(owner.fullName,"Bradley Alexander Abbe")
        assertEquals(owner.address,"Baltimore")
        assertEquals(owner.phone,"23-35-2324")
    }

    @Test
    fun findByPhone() {
        val ownerFromTable = ownerComponent.findByPhone("23-35-2324").get(0)
        assertEquals(ownerFromTable.gender, Gender.MALE )
        assertEquals(ownerFromTable.fullName,"Bradley Alexander Abbe")
        assertEquals(ownerFromTable.address,"Baltimore")
        assertEquals(ownerFromTable.gender,Gender.MALE)
    }

    @Disabled("Disabled until CustomerService is up!")
    @Test
    fun GetAllUsersByGender(){
        ownerComponent.findAll().stream().map { o->o.gender }.collect(Collectors.toSet())
        val map3: Map<Gender,List<Owner>> = ownerComponent.findAll().stream()
            .collect(Collectors.groupingBy(Owner::gender, Collectors.toList()))
        assertEquals(11,21)
    }
}