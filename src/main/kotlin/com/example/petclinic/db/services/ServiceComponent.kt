package com.example.petclinic.db.services

import com.example.petclinic.db.entity.Services
import org.springframework.transaction.annotation.Isolation
import org.springframework.transaction.annotation.Propagation
import org.springframework.transaction.annotation.Transactional
import java.io.IOException
import java.lang.RuntimeException
import java.sql.SQLException

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