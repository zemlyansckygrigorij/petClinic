package com.example.petclinic.web.controllers

import com.example.petclinic.db.entity.Pet
import com.example.petclinic.db.services.PetComponent
import org.springframework.http.HttpStatus
import org.springframework.web.bind.annotation.*

@RestController
@RequestMapping("pet")
class PetController(private val petComponent: PetComponent) {

    @GetMapping
    @ResponseStatus(HttpStatus.FOUND)
    fun findAll() = petComponent.findAll()

    @GetMapping("/{id}")
    @ResponseStatus(HttpStatus.FOUND)
    fun findById(@PathVariable id: Long) = petComponent.findById(id)

    @GetMapping("/{name}")
    @ResponseStatus(HttpStatus.FOUND)
    fun findByName(@PathVariable name: String) = petComponent.findByName(name)

    @GetMapping("/owner/{id}")
    @ResponseStatus(HttpStatus.FOUND)
    fun findByOwnerId(@PathVariable id: Long) = petComponent.findByOwnerId(id)

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    fun create(@RequestBody pet: Pet) = petComponent.save(pet)

    @PutMapping("{id}")
    @ResponseStatus(HttpStatus.OK)
    fun update(@PathVariable id: Long, @RequestBody pet: Pet) = petComponent.save(pet)

    @DeleteMapping("{id}")
    @ResponseStatus(HttpStatus.OK)
    fun delete(@PathVariable id: Long) = petComponent.deleteById(id)

}