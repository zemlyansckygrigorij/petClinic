package com.example.petclinic.db.entity

import java.util.*
import jakarta.persistence.*

/**
 * @author Grigoriy Zemlyanskiy
 * @version 1.0
 * abstract class Person
 */
@MappedSuperclass
abstract class Person(
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    open val id: Long ,

    @Column(name = "full_name", nullable = false)
    open var fullName: String,

    @Column(name = "address", nullable = false)
    open var address: String,

    @Column(name = "phone", nullable = false)
    open var phone: String ,

    @Column(name = "birthday", nullable = true)
    @Temporal(TemporalType.DATE)
    open var birthday: Date,

    @Column(name = "gender", nullable = true)
    @Enumerated(EnumType.STRING)
    open var gender: Gender
)