package com.example.petclinic.transport.mapper

import com.example.petclinic.db.entity.Pet
import com.example.petclinic.transport.dto.PetDto
import com.fasterxml.jackson.module.kotlin.jacksonObjectMapper

/**
 * @author Grigoriy Zemlyanskiy
 * @version 1.0
 * class PetMapper
 */
class PetMapper {
    val mapper = jacksonObjectMapper()
    fun getPetDto(pet: Pet): PetDto? {
        return pet.name?.let {it
            PetDto(
                it,
                pet.kind,
                pet.age,
                pet.gender.toString(),
                pet.idOwner
            )
        }
    }
    fun getJson(pet: Pet):String{
        return mapper.writeValueAsString(getPetDto(pet))
    }
}