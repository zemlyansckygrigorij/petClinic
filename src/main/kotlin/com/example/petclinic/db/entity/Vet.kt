package com.example.petclinic.db.entity

import java.util.*
import jakarta.persistence.*
import lombok.Getter
import lombok.Setter

/**
 * @author Grigoriy Zemlyanskiy
 * @version 1.0
 * class Vet for work with table vet
 */

@Entity
@Table(name = "vet")
class Vet(
    id: Long,
    override var fullName: String,
    override var address: String,
    override var phone: String,
    override var birthday: Date,
    override var gender: Gender,
    qualification: String): Person( id,fullName,address,phone, birthday,gender) {
    @Id  override var id: Long = id
    @Column(name = "qualification", nullable = true)
    var qualification: String? =  qualification
}
