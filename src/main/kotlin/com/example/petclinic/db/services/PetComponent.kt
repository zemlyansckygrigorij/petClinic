package com.example.petclinic.db.services

import com.example.petclinic.db.entity.Pet

/**
 * @author Grigoriy Zemlyanskiy
 * @version 1.0
 * interface PetComponent
 */
interface PetComponent {
    fun save(pet: Pet): Pet
    fun findById(id: Long): Pet
    fun findAll(): ArrayList<Pet>
    fun deleteById(id: Long)
    fun findByName(name: String): ArrayList<Pet>
    fun findByOwnerId(id: Long): ArrayList<Pet>
}