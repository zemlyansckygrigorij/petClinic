package com.example.petclinic.db.entity

import javax.persistence.*

@Entity
@Table(name = "services")
data class Service(

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    val id: Long ,

    @Column(name = "name", nullable = false)
    var name: String,

    @Column(name = " description", nullable = true)
    var description: String? = null,

    @Column(name = "price", nullable = false)
    var price: Double
)