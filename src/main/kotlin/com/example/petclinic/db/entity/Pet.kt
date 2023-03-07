package com.example.petclinic.db.entity

import java.util.*
import javax.persistence.*

@Entity
@Table(name = "pet")
data class Pet(
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    val id: Long ,

    @Column(name = "kind", nullable = false)
    var kind: String,

    @Column(name = "name", nullable = true)
    var name: String? = null,

    @Column(name = "age", nullable = true)
    var age: Int? = null,

    @Column(name = "id_owner", nullable = true)
    var idOwner: Int? = null,

    @Column(name = "gender", nullable = true)
    @Enumerated(EnumType.STRING)
    var gender: Gender)