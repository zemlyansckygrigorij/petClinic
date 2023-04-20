package com.example.petclinic.web.controllers

import com.example.petclinic.db.entity.Pet
import com.example.petclinic.db.services.PetComponent
import com.example.petclinic.transport.service.ProducerService
import com.example.petclinic.web.model.response.PetResponse
import org.springframework.http.HttpStatus
import org.springframework.web.bind.annotation.*

@RestController
@RequestMapping("/pet")
class PetController(private val petComponent: PetComponent,
                    private val producerService: ProducerService
) {

    @GetMapping
    @ResponseStatus(HttpStatus.FOUND)
    fun findAll() = petComponent.findAll().map { pet -> PetResponse.getPetResponse(pet) }

    @GetMapping("/{id}")
    @ResponseStatus(HttpStatus.FOUND)
    fun findById(@PathVariable(name = "id") id: Long) = PetResponse
        .getPetResponse(petComponent.findById(id))

    @GetMapping("/name")
    @ResponseStatus(HttpStatus.FOUND)
    fun findByName(@RequestBody name: String) = petComponent
        .findByName(name)
        .map { pet -> PetResponse.getPetResponse(pet) }


    @GetMapping("/owner/{id}")
    @ResponseStatus(HttpStatus.FOUND)
    fun findByOwnerId(@PathVariable(name = "id") id: Long) =petComponent
        .findByOwnerId(id)
        .map { pet -> PetResponse.getPetResponse(pet) }

    @GetMapping("/{id}/send")
    @ResponseStatus(HttpStatus.FOUND)
    fun sendPet(@PathVariable(name = "id") id: Long)=producerService.producePet(petComponent.findById(id))

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    fun create(@RequestBody pet: Pet): Pet = petComponent.save(pet)

    @PutMapping("/{id}")
    @ResponseStatus(HttpStatus.OK)
    fun update(@PathVariable(name = "id") id: Long, @RequestBody pet: Pet) = petComponent.save(pet)

    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.OK)
    fun delete(@PathVariable(name = "id") id: Long) = petComponent.deleteById(id)

}