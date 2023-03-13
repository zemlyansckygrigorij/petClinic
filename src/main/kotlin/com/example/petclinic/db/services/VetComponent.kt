package com.example.petclinic.db.services

import com.example.petclinic.db.entity.Vet

/**
 * @author Grigoriy Zemlyanskiy
 * @version 1.0
 * interface VetComponent
 */
interface VetComponent {
    fun save(vet: Vet): Vet
    fun findById(id: Long): Vet
    fun findAll(): ArrayList<Vet>
    fun deleteById(id: Long)
    fun findByName(name: String): ArrayList<Vet>
    fun findByPhone(phone: String): Vet
}