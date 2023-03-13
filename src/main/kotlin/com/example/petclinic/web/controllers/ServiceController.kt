package com.example.petclinic.web.controllers

import com.example.petclinic.db.entity.Service
import com.example.petclinic.db.services.ServiceComponent
import org.springframework.http.HttpStatus
import org.springframework.web.bind.annotation.*

@RestController
@RequestMapping("/service")
class ServiceController(private val serviceComponent:ServiceComponent) {

    @GetMapping
    @ResponseStatus(HttpStatus.FOUND)
    fun findAll() = serviceComponent.findAll()

    @GetMapping("/{id}")
    @ResponseStatus(HttpStatus.FOUND)
    fun findById(@PathVariable(name = "id") id:Long)= serviceComponent.findById(id)

    @GetMapping("/name/{name}")
    @ResponseStatus(HttpStatus.FOUND)
    fun findByName(@PathVariable(name = "name") name:String)= serviceComponent.findByName(name)

    @PostMapping()
    @ResponseStatus(HttpStatus.CREATED)
    fun create(@RequestBody service: Service)=serviceComponent.save(service)

    @PutMapping("{id}")
    @ResponseStatus(HttpStatus.OK)
    fun save(@RequestBody service:Service)= serviceComponent.save(service)


    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.OK)
    fun delete(@PathVariable(name = "id") id:Long) =serviceComponent.deleteById(id)
}