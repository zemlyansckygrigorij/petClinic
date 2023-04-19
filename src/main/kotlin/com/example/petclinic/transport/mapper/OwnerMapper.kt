package com.example.petclinic.transport.mapper

import com.example.petclinic.db.entity.Owner
import com.example.petclinic.transport.dto.OwnerDto
import com.fasterxml.jackson.module.kotlin.jacksonObjectMapper

class OwnerMapper {
    val mapper = jacksonObjectMapper()
    fun getOwnerDto(owner: Owner): OwnerDto {
        return OwnerDto(owner.fullName,
            owner.address,
            owner.phone,
            owner.gender.toString(),
            owner.birthday.toString())
    }
    fun getJson(owner: Owner):String{
        var ownerDto:OwnerDto = OwnerDto(owner.fullName,
            owner.address,
            owner.phone,
            owner.gender.toString(),
            owner.birthday.toString())
        return mapper.writeValueAsString(ownerDto)
    }
}