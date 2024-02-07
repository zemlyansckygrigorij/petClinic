package com.example.petclinic.web.controllers

import com.example.petclinic.db.entity.Gender
import com.example.petclinic.db.entity.Owner
import com.example.petclinic.db.services.OwnerComponent
import com.example.petclinic.transport.service.ProducerService
import com.example.petclinic.web.annotation.BadRequest
import com.example.petclinic.web.annotation.InternalServerError
import com.example.petclinic.web.annotation.ResponseOk
import com.example.petclinic.web.model.request.OwnerRequest
import com.example.petclinic.web.model.response.OwnerResponse
import io.swagger.v3.oas.annotations.*
import org.springframework.http.HttpStatus
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.ResponseStatus
import org.springframework.web.bind.annotation.RestController
import org.springframework.web.bind.annotation.*
import java.util.*

import io.swagger.v3.oas.annotations.tags.Tag
import io.swagger.v3.oas.annotations.enums.ParameterStyle
import io.swagger.v3.oas.annotations.media.Content
import org.springframework.http.MediaType

import io.swagger.v3.oas.annotations.responses.ApiResponse
import io.swagger.v3.oas.annotations.responses.ApiResponses

/*
http://localhost:8080/swagger-ui/index.html
*/
/**
 * @author Grigoriy Zemlyanskiy
 * @version 1.0
 * class OwnerController
 */
@RestController
@RequestMapping("/owner")
@Tag(
    name = "API работы с собственниками животных",
    description = "Api work with owners animal",
    externalDocs = ExternalDocumentation(description= "API Documentation",
        url= "https://openweathermap.org/api")
)
class OwnerController(private val ownerComponent: OwnerComponent,
                      private val producerService: ProducerService
) {

    @GetMapping
    @ResponseStatus(HttpStatus.FOUND)
    @Operation(
        description = "Получение всех собственников",
        summary = "Retrieve all owners",
        tags = ["GetMapping", "All" ],
        externalDocs = ExternalDocumentation(description= "API Documentation",
            url= "https://openweathermap.org/api"),
        hidden = false
    )
    @ApiResponse(responseCode = "200", description = "list of OwnerResponse",
        content = arrayOf(
            Content(
                mediaType = MediaType.APPLICATION_JSON_VALUE
            )
        )
    )
    @InternalServerError
    @ResponseOk
    fun findAll()=ownerComponent.findAll().map { owner ->OwnerResponse
        .getOwnerResponse(owner) }

    @GetMapping("/{id}")
    @ResponseStatus(HttpStatus.FOUND)
    @Operation(
        description = "Получение собственника по идентификатору",
        summary = "get owner by Id",
        tags = ["GetMapping","id"],
        externalDocs = ExternalDocumentation(description= "API Documentation",
            url= "https://openweathermap.org/api"),
        hidden = false
    )
    @ApiResponses(
        ApiResponse(responseCode = "200", description = "Ok"),
        ApiResponse(responseCode = "204", description = "No Content")
    )
    @InternalServerError
    @ResponseOk
    @BadRequest
    fun findById(
        @Parameter(
            description = "Идентификатор собственника",
            example = "68406",
            required = true,
            style = ParameterStyle.DEFAULT,
            hidden = false)
        @PathVariable(name = "id") id: Long
    ) = OwnerResponse
        .getOwnerResponse(ownerComponent.findById(id))

    @GetMapping("/name")
    @ResponseStatus(HttpStatus.FOUND)
    @Operation(
        description = "Получение списка собственников по имени",
        summary = "get owners by name",
        tags = ["GetMapping","name"],
        externalDocs = ExternalDocumentation(description= "API Documentation",
            url= "https://openweathermap.org/api"),
        hidden = false
    )
    @ApiResponses(
        ApiResponse(responseCode = "200", description = "Ok"),
        ApiResponse(responseCode = "204", description = "No Content")
    )
    @InternalServerError
    @BadRequest
    fun findByName(
        @Parameter(
            description = "Имя собственника",
            example = "Лена",
            required = true,
            style = ParameterStyle.DEFAULT,
            hidden = false)
        @RequestBody name:String
    )=ownerComponent
        .findByName(name)
        .map { owner ->OwnerResponse
            .getOwnerResponse(owner) }

    @GetMapping("/phone")
    @ResponseStatus(HttpStatus.FOUND)
    @Operation(
        description = "Получение собственника по телефону",
        tags=["GetMapping","phone"],
        externalDocs = ExternalDocumentation(description= "API Documentation",
            url= "https://openweathermap.org/api"),
        summary = "get owner by phone",
        hidden = false
    )
    @ApiResponses(
        ApiResponse(responseCode = "200", description = "OwnerResponse"),
        ApiResponse(responseCode = "204", description = "No Content")

    )
    @InternalServerError
    @BadRequest
    @ResponseOk
    fun findByPhone(
        @Parameter(
            description = "Телефон собственника",
            example = "684-06-09",
            required = true,
            style = ParameterStyle.DEFAULT,
            hidden = false)
        @RequestBody phone: String
    ) = ownerComponent
        .findByPhone(phone)
        .map { owner ->OwnerResponse
            .getOwnerResponse(owner) }

    @GetMapping("/{id}/send")
    @ResponseStatus(HttpStatus.FOUND)
    @Operation(
        description = "Отправка данных собственика",
        tags=["GetMapping","email"],
        externalDocs = ExternalDocumentation(description= "API Documentation",
            url= "https://openweathermap.org/api"),
        summary = "send email about owner",
        hidden = false
    )
    @ApiResponses(
        ApiResponse(responseCode = "200", description = "Ok"),
        ApiResponse(responseCode = "500", description = "Internal Server Error")
    )
    @InternalServerError
    fun sendOwner(
        @Parameter(
            name = "title",
            description = "Идентификатор собственника",
            example = "68406",
            required = true,
            style = ParameterStyle.DEFAULT,
            hidden = false)
        @PathVariable(name = "id") id: Long
    )=producerService
        .produceOwner(ownerComponent.findById(id))


    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    @Operation(
        description = "Создание собственника",
        tags=["PostMapping"],
        externalDocs = ExternalDocumentation(description= "API Documentation",
            url= "https://openweathermap.org/api"),
        summary = "create owner",
        hidden = false
    )
    @ApiResponses(
        ApiResponse(responseCode = "200", description = "Owner"),
        ApiResponse(responseCode = "201", description = "Created")
    )
    @InternalServerError
    @ResponseOk
    fun create(
        @Parameter(
            description = "Данные  собственника",
            example = """{
                            "fullName": "Test123",
                            "address": "Test",
                            "phone": "23-35-32124",
                            "birthday": "1990-01-30",
                            "gender": "MALE"
                        }""",
            required = true,
            style = ParameterStyle.DEFAULT)
        @RequestBody owner: Owner
    ): Owner = ownerComponent.save(owner)

    @PutMapping("/{id}")
    @ResponseStatus(HttpStatus.OK)
    @Operation(
        description = "Обновление собственника",
        tags=["PutMapping"],
        externalDocs = ExternalDocumentation(description= "API Documentation",
            url= "https://openweathermap.org/api"),
        summary = "update owner by Id",
        hidden = false
    )
    @Parameters(
            Parameter(name = "id", description = "Идентификатор собственника",required = true, hidden = false),
            Parameter(name = "ownerRequest", description = "Данные  собственника", required = true, hidden = false)
        )
    @ApiResponses(
        ApiResponse(responseCode = "400", description = "Bad Request"),
        ApiResponse(responseCode = "200", description = "OK")
    )
    @InternalServerError
    @ResponseOk
    fun update(
        @Parameter(
            description = "Идентификатор собственника",
            example = "68406",
            required = true,
            style = ParameterStyle.DEFAULT,
            hidden = false)
        @PathVariable(name = "id") id:Long,

        @Parameter(
            description = "Данные  собственника",
            example = """{
                            "full_name": "Test111",
                            "address": "Test222",
                            "phone": "23-35-232433",
                            "birthday": "1990-01-3044",
                            "gender": "MALE"
                        }""",
            required = true,
            style = ParameterStyle.DEFAULT,
            hidden = false)
        @RequestBody ownerRequest: OwnerRequest
    ) {

        val ownerFromTable = ownerComponent.findById(id)
        if(ownerRequest.gender.equals("MALE"))ownerFromTable.gender = Gender.MALE
        if(ownerRequest.gender.equals("FEMALE"))ownerFromTable.gender = Gender.FEMALE
        ownerFromTable.address = ownerRequest.address
        ownerFromTable.birthday = ownerRequest.birthday
        ownerFromTable.phone = ownerRequest.phone
        ownerFromTable.fullName = ownerRequest.fullName
        ownerComponent.save(ownerFromTable)
    }

    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.OK)
    //@Hidden
    @Operation(
        description = "Удаление собственника",
        tags=["DeleteMapping"],
        externalDocs = ExternalDocumentation(description= "API Documentation",
            url= "https://openweathermap.org/api"),
        summary = "delete owner by Id",
        hidden = false
    )
    @ApiResponses(
        ApiResponse(responseCode = "200", description = "Ok"),
        ApiResponse(responseCode = "400", description = "Bad Request")
    )
    @InternalServerError
    @ResponseOk
    fun delete(
        @Parameter(
            description = "Идентификатор собственника",
            example = "68406",
            required = true,
            style = ParameterStyle.DEFAULT)
        @PathVariable(name = "id") id:Long
    ) = ownerComponent.deleteById(id)
}





