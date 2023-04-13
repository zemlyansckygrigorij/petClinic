package com.example.petclinic.transport.service

import com.example.petclinic.db.entity.Gender
import com.example.petclinic.db.entity.Owner

import com.fasterxml.jackson.databind.ObjectMapper
import com.fasterxml.jackson.module.kotlin.jacksonObjectMapper
import jakarta.persistence.*
import java.util.*

data class OwnerDto( var fullName: String,
                     var address: String,
                     var phone: String,
                     var gender: String,
                     var birthday: String)
data class PetDto( var  name: String,
                     var kind: String,
                     var age: Int,
                     var gender: String,
                     var idOwner: Int)

data class ServiceDto( var name: String,
                     var description: String,
                     var price: Double)

data class VetDto( var fullName: String,
                     var address: String,
                     var phone: String,
                     var gender: String,
                     var birthday: String,
                     var qualification: String)

val mapper = jacksonObjectMapper()
fun getOwnerJson(owner: Owner):String{
    return mapper
        .writeValueAsString(OwnerDto( owner.fullName,
    owner.address,
    owner.phone,
    owner.gender.toString(),
    owner.birthday.toString()))
   // return mapper.writeValueAsString(OwnerDto(owner))
  //  return ObjectMapper().writeValueAsString(OwnerDto(owner))
}