package com.example.petclinic.db.entity

import java.util.*
import jakarta.persistence.*

/**
 * @author Grigoriy Zemlyanskiy
 * @version 1.0
 * class Vet
 */
@Entity
@Table(name = "vet")
class Vet(
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    val id: Long ,

    @Column(name = "full_name", nullable = false)
    var fullName: String,

    @Column(name = "address", nullable = true)
    var address: String? = null,

    @Column(name = "phone", nullable = true)
    var phone: String? = null,

    @Column(name = "birthday", nullable = true)
    @Temporal(TemporalType.DATE)
    var birthday: Date? = null,

    @Column(name = "gender", nullable = true)
    @Enumerated(EnumType.STRING)
    var gender: Gender,

    @Column(name = "qualification", nullable = true)
    var qualification: String? = null)
