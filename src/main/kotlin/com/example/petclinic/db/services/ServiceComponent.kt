package com.example.petclinic.db.services

import com.example.petclinic.db.entity.Services

/**
 * @author Grigoriy Zemlyanskiy
 * @version 1.0
 * interface ServiceComponent
 */
interface ServiceComponent {
    fun save(service: Services): Services
    fun findById(id: Long): Services
    fun findAll(): ArrayList<Services>
    fun deleteById(id: Long)
    fun findByName(name: String): ArrayList<Services>
}