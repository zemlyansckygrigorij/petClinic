package com.example.petclinic.db.services

import com.example.petclinic.db.entity.Owner

interface OwnerComponent {
    fun save(owner: Owner)
    fun findById(id: Long) : Owner
    fun findAll(): ArrayList<Owner>
    fun deleteById(id: Long)
    fun findByName(name: String) : ArrayList<Owner>
    fun findByPhone(phone: String) : Owner
}