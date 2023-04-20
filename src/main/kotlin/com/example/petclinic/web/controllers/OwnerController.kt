package com.example.petclinic.web.controllers

import com.example.petclinic.db.entity.Gender
import com.example.petclinic.db.entity.Owner
import com.example.petclinic.db.services.OwnerComponent
import com.example.petclinic.transport.service.ProducerService
import com.example.petclinic.web.model.request.OwnerRequest
import com.example.petclinic.web.model.response.OwnerResponse
import jakarta.persistence.*
import org.springframework.http.HttpStatus
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.ResponseStatus
import org.springframework.web.bind.annotation.RestController
import org.springframework.web.bind.annotation.*
import java.util.*

@RestController
@RequestMapping("/owner")
class OwnerController(private val ownerComponent: OwnerComponent,
                      private val producerService: ProducerService
) {

    @GetMapping
    @ResponseStatus(HttpStatus.FOUND)
    fun findAll()=ownerComponent.findAll().map { owner ->OwnerResponse
        .getOwnerResponse(owner) }

    @GetMapping("/{id}")
    @ResponseStatus(HttpStatus.FOUND)
    fun findById(@PathVariable(name = "id") id: Long) = OwnerResponse
        .getOwnerResponse(ownerComponent.findById(id))//ownerComponent.findById(id)

    @GetMapping("/name")
    @ResponseStatus(HttpStatus.FOUND)
    fun findByName(@RequestBody name:String)=ownerComponent
        .findByName(name)
        .map { owner ->OwnerResponse
        .getOwnerResponse(owner) }

    @GetMapping("/phone")
    @ResponseStatus(HttpStatus.FOUND)
    fun findByPhone(@RequestBody phone: String) = OwnerResponse
        .getOwnerResponse(ownerComponent.findByPhone(phone))

    @GetMapping("/{id}/send")
    @ResponseStatus(HttpStatus.FOUND)
    fun sendOwner(@PathVariable(name = "id") id: Long)=producerService
        .produceOwner(ownerComponent.findById(id))


    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    fun create(@RequestBody owner: Owner): Owner = ownerComponent.save(owner)

    @PutMapping("/{id}")
    @ResponseStatus(HttpStatus.OK)
    fun update(@PathVariable(name = "id") id:Long, @RequestBody ownerRequest:OwnerRequest) {
        var ownerFromTable = ownerComponent.findById(id)
        if(ownerRequest.gender.equals("MALE"))ownerFromTable.gender = Gender.MALE
        if(ownerRequest.gender.equals("FEMALE"))ownerFromTable.gender = Gender.FEMALE
        ownerFromTable.address = ownerRequest.address
        ownerFromTable.birthday =ownerRequest.birthday
        ownerFromTable.phone = ownerRequest.phone
        ownerFromTable.fullName = ownerRequest.fullName
        ownerComponent.save(ownerFromTable)
    }

    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.OK)
    fun delete(@PathVariable(name = "id") id:Long) = ownerComponent.deleteById(id)
}












