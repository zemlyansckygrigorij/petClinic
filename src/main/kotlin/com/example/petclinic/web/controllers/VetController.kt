package com.example.petclinic.web.controllers

import com.example.petclinic.db.entity.Vet
import com.example.petclinic.db.services.VetComponent
import org.springframework.http.HttpStatus
import org.springframework.web.bind.annotation.*

@RestController
@RequestMapping("/vet")
class VetController(private val vetComponent: VetComponent) {

    @GetMapping
    @ResponseStatus(HttpStatus.FOUND)
    fun findAll() = vetComponent.findAll()

    @GetMapping("/{id}")
    @ResponseStatus(HttpStatus.FOUND)
    fun findById(@PathVariable(name = "id") id:Long) = vetComponent.findById(id)

    @GetMapping("/name/{name}")
    @ResponseStatus(HttpStatus.FOUND)
    fun findByName(@PathVariable(name = "name") name:String)= vetComponent.findByName(name)

    @GetMapping("/phone/{phone}")
    @ResponseStatus(HttpStatus.FOUND)
    fun findByPhone(@PathVariable(name = "phone") phone: String) = vetComponent.findByPhone(phone)

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    fun create(@RequestBody vet: Vet) = vetComponent.save(vet)

    @PutMapping("/{id}")
    @ResponseStatus(HttpStatus.OK)
    fun update(@PathVariable(name = "id") id:Long, @RequestBody vet:Vet) = vetComponent.save(vet)

    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.OK)
    fun delete(@PathVariable(name = "id") id: Long) = vetComponent.deleteById(id)

}