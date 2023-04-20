package com.example.petclinic.web.controllers

import com.example.petclinic.db.entity.Vet
import com.example.petclinic.db.services.VetComponent
import com.example.petclinic.transport.service.ProducerService
import com.example.petclinic.web.model.response.VetResponse
import org.springframework.http.HttpStatus
import org.springframework.web.bind.annotation.*

@RestController
@RequestMapping("/vet")
class VetController(private val vetComponent: VetComponent,
                    private val producerService: ProducerService
) {

    @GetMapping
    @ResponseStatus(HttpStatus.FOUND)
    fun findAll() = vetComponent.findAll().map{
            vet ->VetResponse
        .getVetResponse(vet)
    }

    @GetMapping("/{id}")
    @ResponseStatus(HttpStatus.FOUND)
    fun findById(@PathVariable(name = "id") id:Long) = VetResponse
        .getVetResponse(vetComponent.findById(id))

    @GetMapping("/name")
    @ResponseStatus(HttpStatus.FOUND)
    fun findByName(@RequestBody name:String)= vetComponent.findByName(name).map{
        vet ->VetResponse
        .getVetResponse(vet)
    }

    @GetMapping("/phone")
    @ResponseStatus(HttpStatus.FOUND)
    fun findByPhone(@RequestBody phone: String) = VetResponse
        .getVetResponse(vetComponent.findByPhone(phone))

    @GetMapping("/{id}/send")
    @ResponseStatus(HttpStatus.FOUND)
    fun sendVet(@PathVariable(name = "id") id: Long)=producerService.produceVet(vetComponent.findById(id))//producerService.produce(ownerComponent.findById(id).fullName)

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