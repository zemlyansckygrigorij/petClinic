package com.example.petclinic.db.entity

import java.util.*
import jakarta.persistence.*

@Entity
@Table(name = "owner",schema = "public")
class Owner(
    @Id
   @GeneratedValue(strategy = GenerationType.IDENTITY)
     val id: Long,

    @Column(name = "fullname", nullable = false)
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