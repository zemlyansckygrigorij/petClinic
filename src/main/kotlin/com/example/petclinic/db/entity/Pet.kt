package com.example.petclinic.db.entity

import jakarta.persistence.*

/**
 * @author Grigoriy Zemlyanskiy
 * @version 1.0
 * class Pet
 */
@Entity
@Table(name = "pet", schema = "public")
class Pet(
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    val id: Long,

    @Column(name = "kind", nullable = false)
    val kind: String,

    @Column(name = "name", nullable = true)
    val name: String? = null,

    @Column(name = "age", nullable = true)
    val age: Int? = null,

    @Column(name = "id_owner", nullable = true)
    val idOwner: Int? = null,

    @Column(name = "gender", nullable = true)
    @Enumerated(EnumType.STRING)
    val gender: Gender? = null
)