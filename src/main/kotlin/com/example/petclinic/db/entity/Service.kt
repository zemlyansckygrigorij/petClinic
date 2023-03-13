package com.example.petclinic.db.entity

import jakarta.persistence.*


/**
 * @author Grigoriy Zemlyanskiy
 * @version 1.0
 * class Service
 */
@Entity
@Table(name = "services",schema = "public")
class Service(

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    val id: Long,

    @Column(name = "name", nullable = false)
    val name: String,

    @Column(name = " description", nullable = true)
    val description: String? = null,

    @Column(name = "price", nullable = false)
    val price: Double
)