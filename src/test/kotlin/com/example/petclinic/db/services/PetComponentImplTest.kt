package com.example.petclinic.db.services

import com.example.petclinic.db.entity.Gender
import com.example.petclinic.db.entity.Pet
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.Assertions.*
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.context.SpringBootTest

@SpringBootTest
class PetComponentImplTest @Autowired constructor(val petComponent: PetComponent) {

    @Test
    fun save() {
        val pets = petComponent.findAll()
        assertEquals(pets.size,21)
        val id = pets.maxBy { it.id }.id+1
        val pet = Pet(id,"CAT","Smokey",1,1,Gender.FEMALE)
        val petSave = petComponent.save(pet)
        val petFromTable = petComponent.findById(petSave.id)
        assertEquals(petFromTable.id,petSave.id)
        assertEquals(petFromTable.name,petSave.name)
        assertEquals(petFromTable.age,petSave.age)
        assertEquals(petFromTable.gender,petSave.gender)
        assertEquals(petFromTable.kind,petSave.kind)
        assertEquals(petFromTable.idOwner,petSave.idOwner)
        assertEquals(petComponent.findAll().size,22)
        petComponent.deleteById(petFromTable.id)
        assertEquals(petComponent.findAll().size,21)
    }

    @Test
    fun findById() {
        val pet = petComponent.findById(1)
        assertEquals(pet.id,1)
        assertEquals(pet.name,"Oliver")
        assertEquals(pet.age,1)
        assertEquals(pet.gender, Gender.MALE)
        assertEquals(pet.kind,"CAT")
        assertEquals(pet.idOwner,1)
    }

    @Test
    fun findAll() {
        val pets = petComponent.findAll()
        assertEquals(pets.size,21)
    }

    @Test
    fun deleteById() {
    }

    @Test
    fun findByName() {
        val pets = petComponent.findByName("Bella")
        assertEquals(pets.size,2)
        val pet = pets[0]
        assertEquals(pet.id,8)
        assertEquals(pet.name,"Bella")
        assertEquals(pet.age,8)
        assertEquals(pet.gender, Gender.FEMALE)
        assertEquals(pet.kind,"CAT")
    }

    @Test
    fun findByOwnerId() {
        val pets = petComponent.findByOwnerId(2)
        assertEquals(pets.size,2)
        val pet = pets[0]
        assertEquals(pet.id,2)
        assertEquals(pet.name,"Leo")
        assertEquals(pet.age,2)
        assertEquals(pet.gender, Gender.MALE)
        assertEquals(pet.kind,"CAT")
    }
}