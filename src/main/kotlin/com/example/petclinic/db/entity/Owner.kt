package com.example.petclinic.db.entity

import java.util.*
import jakarta.persistence.*
import lombok.Getter
import lombok.Setter

/**
 * @author Grigoriy Zemlyanskiy
 * @version 1.0
 * class Owner for work with table owner
 */
@Entity
@Table(name = "owner",schema = "public")
class Owner(
    id: Long,
    override var fullName: String,
    override var address: String,
    override var phone: String,
    override var birthday: Date,
    override var gender: Gender
): Person( id,fullName,address,phone, birthday,gender) {
    @Id  override var id: Long = id
}


