package com.example.petclinic.db.entity

import jakarta.persistence.*


/**
 * @author Grigoriy Zemlyanskiy
 * @version 1.0
 * class Service
 */
@Entity
@Table(name = "services",schema = "public")
class Services(

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    val id: Long,

    @Column(name = "name", nullable = false)
    var name: String,

    @Column(name = " description", nullable = true)
    var description: String? = null,

    @Column(name = "price", nullable = false)
    var price: Double
)