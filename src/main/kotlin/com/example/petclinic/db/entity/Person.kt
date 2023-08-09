package com.example.petclinic.db.entity

import java.util.*
import jakarta.persistence.*

/**
 * @author Grigoriy Zemlyanskiy
 * @version 1.0
 * abstract class Person
 */
@Entity
abstract class Person(
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    open val id: Long? ,

    @Column(name = "fullname", nullable = false)
    open var fullName: String,

    @Column(name = "address", nullable = true)
    open var address: String? = null,

    @Column(name = "phone", nullable = true)
    open var phone: String? = null,

    @Column(name = "birthday", nullable = true)
    @Temporal(TemporalType.DATE)
    open var birthday: Date? = null,

    @Column(name = "gender", nullable = true)
    @Enumerated(EnumType.STRING)
    open var gender: Gender
)