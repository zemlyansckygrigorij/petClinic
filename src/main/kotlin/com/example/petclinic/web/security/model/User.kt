package com.example.petclinic.web.security.model

/**
 * @author Grigoriy Zemlyanskiy
 * @version 1.0
 * class User
 */
data class User(
    val id: Long,
    val username: String,
    val password: String,
    val role:ROLE
)
