package com.example.petclinic.web.controllers

import com.example.petclinic.db.entity.Owner
import com.example.petclinic.db.services.OwnerComponent
import org.springframework.http.HttpStatus
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.ResponseStatus
import org.springframework.web.bind.annotation.RestController
import org.springframework.web.bind.annotation.*
@RestController
@RequestMapping("owner")
class OwnerController(private val ownerComponent:OwnerComponent) {
    @GetMapping
    @ResponseStatus(HttpStatus.FOUND)
    fun findAll()=ownerComponent.findAll()

    @GetMapping("/{id}")
    @ResponseStatus(HttpStatus.FOUND)
    fun findById(@PathVariable id:Long)=ownerComponent.findById(id)
    @GetMapping("/{name}")
    @ResponseStatus(HttpStatus.FOUND)
    fun findByName(@PathVariable name:String)=ownerComponent.findByName(name)

    @GetMapping("/phone/{phone}")
    @ResponseStatus(HttpStatus.FOUND)
    fun findByPhone(@PathVariable phone: String) = ownerComponent.findByPhone(phone)

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    fun create(@RequestBody owner: Owner) = ownerComponent.save(owner)

    @PutMapping("/{id}")
    @ResponseStatus(HttpStatus.OK)
    fun update(@PathVariable id:Long,@RequestBody owner:Owner)= ownerComponent.save(owner)

    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.OK)
    fun delete(@PathVariable id:Long) = ownerComponent.deleteById(id)
}












