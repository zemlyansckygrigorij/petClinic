package com.example.petclinic.db.entity

import java.util.*
import jakarta.persistence.*
import org.springframework.format.annotation.DateTimeFormat

/**
 * @author Grigoriy Zemlyanskiy
 * @version 1.0
 * class Owner
 */
@Entity
@Table(name = "owner",schema = "public")
data class Owner(
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
     val id: Long?=null,

    @Column(name = "full_name", nullable = false)
     var fullName: String,

    @Column(name = "address", nullable = true)
     var address: String,

    @Column(name = "phone", nullable = true)
     var phone: String,

    @Column(name = "birthday", columnDefinition = "DATE", nullable = true)
    @DateTimeFormat(pattern="yyyy-MM-dd")
    @Temporal(TemporalType.DATE)
     var birthday: Date,

    @Column(name = "gender", nullable = true)
    @Enumerated(EnumType.STRING)
     var gender: Gender
)


