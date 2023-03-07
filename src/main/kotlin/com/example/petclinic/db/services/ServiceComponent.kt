package com.example.petclinic.db.services

import com.example.petclinic.db.entity.Service

interface ServiceComponent {
    fun save(service: Service)
    fun findById(id: Long): Service
    fun findAll(): ArrayList<Service>
    fun deleteById(id: Long)
    fun findByName(name: String): ArrayList<Service>
}