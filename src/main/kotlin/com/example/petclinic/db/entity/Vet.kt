package com.example.petclinic.db.entity

import org.springframework.data.annotation.Id
import java.util.*
import jakarta.persistence.*

/**
 * @author Grigoriy Zemlyanskiy
 * @version 1.0
 * class Vet
 */
@Table(name = "vet",schema = "public")
class Vet(
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    val id: Long ,

    @Column(name = "fullname", nullable = false)
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
