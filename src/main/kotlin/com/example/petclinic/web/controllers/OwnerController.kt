package com.example.petclinic.web.controllers

import com.example.petclinic.db.entity.Gender
import com.example.petclinic.db.entity.Owner
import com.example.petclinic.db.services.OwnerComponent
import com.example.petclinic.transport.service.ProducerService
import com.example.petclinic.web.model.request.OwnerRequest
import com.example.petclinic.web.model.response.OwnerResponse
import org.springframework.http.HttpStatus
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.ResponseStatus
import org.springframework.web.bind.annotation.RestController
import org.springframework.web.bind.annotation.*
import java.util.*

import io.swagger.v3.oas.annotations.tags.Tag;
import io.swagger.v3.oas.annotations.Operation
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.enums.ParameterStyle;
/*
https://www.bezkoder.com/spring-boot-swagger-3/
https://www.bezkoder.com/swagger-3-annotations/
*/
@RestController
@RequestMapping("/owner")
@Tag(name = "API работы с собственниками животных")
class OwnerController(private val ownerComponent: OwnerComponent,
                      private val producerService: ProducerService
) {

    @GetMapping
    @ResponseStatus(HttpStatus.FOUND)
    @Operation(description = "Получение всех собственников")
    fun findAll()=ownerComponent.findAll().map { owner ->OwnerResponse
        .getOwnerResponse(owner) }

    @GetMapping("/{id}")
    @ResponseStatus(HttpStatus.FOUND)
    @Operation(description = "Получение собственника по идентификатору")
    fun findById(
        @Parameter(description = "Идентификатор собственника", example = "68406", required = true,
            style = ParameterStyle.DEFAULT)
        @PathVariable(name = "id") id: Long
    ) = OwnerResponse
        .getOwnerResponse(ownerComponent.findById(id))//ownerComponent.findById(id)

    @GetMapping("/name")
    @ResponseStatus(HttpStatus.FOUND)
    @Operation(description = "Получение списка собственников по имени")
    fun findByName(
        @Parameter(description = "Имя собственника", example = "Лена", required = true,
            style = ParameterStyle.DEFAULT)
        @RequestBody name:String
    )=ownerComponent
        .findByName(name)
        .map { owner ->OwnerResponse
        .getOwnerResponse(owner) }

    @GetMapping("/phone")
    @ResponseStatus(HttpStatus.FOUND)
    @Operation(description = "Получение собственника по телефону")
    fun findByPhone(
        @Parameter(description = "Телефон собственника", example = "684-06-09", required = true,
            style = ParameterStyle.DEFAULT)
        @RequestBody phone: String
    ) = OwnerResponse
        .getOwnerResponse(ownerComponent.findByPhone(phone))

    @GetMapping("/{id}/send")
    @ResponseStatus(HttpStatus.FOUND)
    @Operation(description = "Отправка данных собственика")
    fun sendOwner(
        @Parameter(description = "Идентификатор собственника", example = "68406", required = true,
            style = ParameterStyle.DEFAULT)
        @PathVariable(name = "id") id: Long
    )=producerService
        .produceOwner(ownerComponent.findById(id))


    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    @Operation(description = "Создание собственника")
    fun create(
        @Parameter(description = "Данные  собственника", example = "68406", required = true,
            style = ParameterStyle.DEFAULT)
        @RequestBody owner: Owner
    ): Owner = ownerComponent.save(owner)

    @PutMapping("/{id}")
    @ResponseStatus(HttpStatus.OK)
    @Operation(description = "Обновление собственника")
    fun update(
        @Parameter(description = "Идентификатор собственника", example = "68406", required = true,
            style = ParameterStyle.DEFAULT)
        @PathVariable(name = "id") id:Long,

        @Parameter(description = "Идентификатор собственника", example = "68406", required = true,
            style = ParameterStyle.DEFAULT)
        @RequestBody ownerRequest:OwnerRequest
    ) {
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
    @Operation(description = "Удаление собственника")
    fun delete(
        @Parameter(description = "Идентификатор собственника", example = "68406", required = true,
            style = ParameterStyle.DEFAULT)
        @PathVariable(name = "id") id:Long
    ) = ownerComponent.deleteById(id)
}












