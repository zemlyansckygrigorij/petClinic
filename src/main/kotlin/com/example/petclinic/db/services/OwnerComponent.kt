package com.example.petclinic.db.services

import com.example.petclinic.db.entity.Owner
import org.springframework.transaction.annotation.Transactional
import java.util.*

/**
 * @author Grigoriy Zemlyanskiy
 * @version 1.0
 * interface OwnerComponent
 */
interface OwnerComponent {
    fun save(owner: Owner): Owner
    fun findById(id: Long) : Owner
    fun findAll(): ArrayList<Owner>
    fun deleteById(id: Long)
    fun findByName(name: String) : ArrayList<Owner>
    fun findByPhone(phone: String) : ArrayList<Owner>
}