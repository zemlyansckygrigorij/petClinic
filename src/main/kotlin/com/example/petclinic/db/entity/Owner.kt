package com.example.petclinic.db.entity

import java.util.*
import jakarta.persistence.*

/**
 * @author Grigoriy Zemlyanskiy
 * @version 1.0
 * data class Owner
 */
@Entity
@Table(name = "owner",schema = "public")
class Owner(
    @Id
   @GeneratedValue(strategy = GenerationType.IDENTITY)
     val id: Long,

    @Column(name = "full_name", nullable = false)
     var fullName: String,

    @Column(name = "address", nullable = true)
     var address: String,

    @Column(name = "phone", nullable = true)
     var phone: String,

    @Column(name = "birthday", nullable = true)
    @Temporal(TemporalType.DATE)
     var birthday: Date,

    @Column(name = "gender", nullable = true)
    @Enumerated(EnumType.STRING)
     var gender: Gender
)


