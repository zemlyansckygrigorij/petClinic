package com.example.petclinic.db.entity

import java.util.*
import javax.persistence.Column
import javax.persistence.Table

/**
 * @author Grigoriy Zemlyanskiy
 * @version 1.0
 * data class Vet
 */
@Table(name = "vet")
data class Vet(
    override val id: Long,
    override var fullName: String,
    override var address: String? = null,
    override var phone: String? = null,
    override var birthday: Date? = null,
    override var gender: Gender,
    @Column(name = "qualification", nullable = true)
    var qualification: String? = null)
    :Person(id,fullName,address,phone,birthday,gender)