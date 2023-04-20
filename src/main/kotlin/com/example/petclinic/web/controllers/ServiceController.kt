package com.example.petclinic.web.controllers

import com.example.petclinic.db.entity.Services
import com.example.petclinic.db.services.ServiceComponent
import com.example.petclinic.transport.service.ProducerService
import com.example.petclinic.web.model.response.ServiceResponse
import org.springframework.http.HttpStatus
import org.springframework.web.bind.annotation.*

@RestController
@RequestMapping("/service")
class ServiceController(private val serviceComponent:ServiceComponent,
                        private val producerService: ProducerService
) {

    @GetMapping
    @ResponseStatus(HttpStatus.FOUND)
    fun findAll() = serviceComponent.findAll().map { service -> ServiceResponse
        .getServiceResponse(service)}

    @GetMapping("/{id}")
    @ResponseStatus(HttpStatus.FOUND)
    fun findById(@PathVariable(name = "id") id:Long)= ServiceResponse
        .getServiceResponse(serviceComponent.findById(id))

    @GetMapping("/name")
    @ResponseStatus(HttpStatus.FOUND)
    fun findByName(@RequestParam name:String)= serviceComponent.findByName(name).map { service -> ServiceResponse
        .getServiceResponse(service)}

    @GetMapping("/{id}/send")
    @ResponseStatus(HttpStatus.FOUND)
    fun sendService(@PathVariable(name = "id") id: Long)=producerService.produceService(serviceComponent.findById(id))

    @PostMapping()
    @ResponseStatus(HttpStatus.CREATED)
    fun create(@RequestBody service: Services)=serviceComponent.save(service)

    @PutMapping("{id}")
    @ResponseStatus(HttpStatus.OK)
    fun save(@RequestBody service:Services)= serviceComponent.save(service)


    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.OK)
    fun delete(@PathVariable(name = "id") id:Long) =serviceComponent.deleteById(id)
}