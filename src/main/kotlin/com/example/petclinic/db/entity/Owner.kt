package com.example.petclinic.db.entity
import java.util.*
import javax.persistence.*

@Table(name = "owner")
data class Owner(
    override val id: Long,
    override var fullName: String,
    override var address: String? = null,
    override var phone: String? = null,
    override var birthday: Date? = null,
    override var gender: Gender)
    :Person(id,fullName,address,phone,birthday,gender)