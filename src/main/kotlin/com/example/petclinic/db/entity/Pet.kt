package com.example.petclinic.db.entity

import jakarta.persistence.*

/**
 * @author Grigoriy Zemlyanskiy
 * @version 1.0
 * class Pet for work with table pet
 */
@Entity
@Table(name = "pet", schema = "public")
class Pet(
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    val id: Long,

    @Column(name = "kind", nullable = false)
    var kind: String,

    @Column(name = "name", nullable = true)
    var name: String? = null,

    @Column(name = "age", nullable = true)
    var age: Int? = null,

    @Column(name = "owner_id", nullable = true)
    var idOwner: Int? = null,

    @Column(name = "gender", nullable = true)
    @Enumerated(EnumType.STRING)
    var gender: Gender? = null
)