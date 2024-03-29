package com.example.petclinic.transport.mapper

import com.example.petclinic.db.entity.Vet
import com.example.petclinic.transport.dto.VetDto
import com.fasterxml.jackson.module.kotlin.jacksonObjectMapper

class VetMapper {
    val mapper = jacksonObjectMapper()
    fun getVetDto(vet: Vet): VetDto? {
        return vet.address?.let { address->
            vet.phone?.let { phone ->
                VetDto(
                    vet.fullName,
                    address,
                    phone,
                    vet.gender.toString(),
                    vet.qualification,
                    vet.birthday.toString()
                )
            }
        }

    }
    fun getJson(vet: Vet):String{
        return mapper.writeValueAsString(getVetDto(vet))
    }
}