package com.example.petclinic.db.services

import com.example.petclinic.db.entity.Pet

interface PetComponent {
    fun save(pet: Pet)
    fun findById(id: Long): Pet
    fun findAll(): ArrayList<Pet>
    fun deleteById(id: Long)
    fun findByName(name: String): ArrayList<Pet>
    fun findByOwnerId(id: Long): ArrayList<Pet>
}