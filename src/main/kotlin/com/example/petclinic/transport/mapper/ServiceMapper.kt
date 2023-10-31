package com.example.petclinic.transport.mapper


import com.example.petclinic.db.entity.Services
import com.example.petclinic.transport.dto.ServiceDto
import com.fasterxml.jackson.module.kotlin.jacksonObjectMapper

/**
 * @author Grigoriy Zemlyanskiy
 * @version 1.0
 * class ServiceMapper
 */
class ServiceMapper {
    val mapper = jacksonObjectMapper()
    fun getServicesDto(service: Services): ServiceDto? {
        return service.description?.let {it
            ServiceDto(service.name,
                it,
                service.price,
            )
        }
    }
    fun getJson(services: Services):String{
        return mapper.writeValueAsString(getServicesDto(services))
    }
}