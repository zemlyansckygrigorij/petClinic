package com.example.petclinic.web.controllers

import com.example.petclinic.db.entity.Gender
import com.example.petclinic.db.entity.Vet
import com.example.petclinic.db.services.VetComponent
import com.example.petclinic.transport.service.ProducerService
import com.example.petclinic.web.annotation.InternalServerError
import com.example.petclinic.web.annotation.ResponseOk
import com.example.petclinic.web.model.request.VetRequest
import com.example.petclinic.web.model.response.VetResponse
import io.swagger.v3.oas.annotations.ExternalDocumentation
import io.swagger.v3.oas.annotations.Operation
import io.swagger.v3.oas.annotations.Parameter
import io.swagger.v3.oas.annotations.Parameters
import io.swagger.v3.oas.annotations.enums.ParameterStyle
import io.swagger.v3.oas.annotations.responses.ApiResponse
import io.swagger.v3.oas.annotations.responses.ApiResponses
import io.swagger.v3.oas.annotations.tags.Tag
import org.springframework.http.HttpStatus
import org.springframework.web.bind.annotation.*

/**
 * @author Grigoriy Zemlyanskiy
 * @version 1.0
 * class VetController
 */
@RestController
@RequestMapping("/vet")
@Tag(
    name = "API работы со специалистами",
    description = "Api work with vets",
    externalDocs = ExternalDocumentation(description= "API Documentation",
        url= "https://openweathermap.org/api")
)
class VetController(private val vetComponent: VetComponent,
                    private val producerService: ProducerService
) {
    @GetMapping
    @ResponseStatus(HttpStatus.FOUND)
    @Operation(
        description = "Получение всех специалистов",
        summary = "Retrieve all vets",
        tags = ["GetMapping", "All" ],
        externalDocs = ExternalDocumentation(description= "API Documentation",
            url= "https://openweathermap.org/api"),
        hidden = false
    )
    @ApiResponse(responseCode = "200", description = "list of VetResponse")
    @InternalServerError
    @ResponseOk
    fun findAll() = vetComponent.findAll().map{
            vet ->VetResponse
        .getVetResponse(vet)
    }

    @GetMapping("/{id}")
    @ResponseStatus(HttpStatus.FOUND)
    @Operation(
        description = "Получение специалиста по идентификатору",
        summary = "Retrieve a vet by Id",
        tags = ["GetMapping", "id" ],
        externalDocs = ExternalDocumentation(description= "API Documentation",
            url= "https://openweathermap.org/api"),
        hidden = false)
    @ApiResponses(
        ApiResponse(responseCode = "200", description = "VetResponse"),
        ApiResponse(responseCode = "204", description = "No Content")
    )
    @InternalServerError
    @ResponseOk
    fun findById(
        @Parameter(
            description = "Идентификатор специалиста",
            example = "68406",
            required = true,
            style = ParameterStyle.DEFAULT,
            hidden = false)
        @PathVariable(name = "id") id:Long) = VetResponse
        .getVetResponse(vetComponent.findById(id))

    @GetMapping("/name")
    @ResponseStatus(HttpStatus.FOUND)
    @Operation(
        description = "Получение списка специалистов по имени",
        summary = "get vets by name",
        tags = ["GetMapping","name"],
        externalDocs = ExternalDocumentation(description= "API Documentation",
            url= "https://openweathermap.org/api"),
        hidden = false
    )
    @ApiResponses(
        ApiResponse(responseCode = "200", description = "list of VetResponse"),
        ApiResponse(responseCode = "204", description = "No Content")
    )
    @InternalServerError
    @ResponseOk
    fun findByName(
        @Parameter(
            description = "Имя специалиста",
            example = "Лена",
            required = true,
            style = ParameterStyle.DEFAULT,
            hidden = false)
        @RequestBody name:String)= vetComponent.findByName(name).map{
            vet ->VetResponse
        .getVetResponse(vet)
    }

    @GetMapping("/phone")
    @ResponseStatus(HttpStatus.FOUND)
    @Operation(
        description = "Получение специалиста по телефону",
        tags=["GetMapping","phone"],
        externalDocs = ExternalDocumentation(description= "API Documentation",
            url= "https://openweathermap.org/api"),
        summary = "get vet by phone",
        hidden = false
    )
    @ApiResponses(
        ApiResponse(responseCode = "200", description = "VetResponse"),
        ApiResponse(responseCode = "204", description = "No Content")
    )
    @InternalServerError
    @ResponseOk
    fun findByPhone(@RequestBody phone: String) = VetResponse
        .getVetResponse(vetComponent.findByPhone(phone))

    @GetMapping("/{id}/send")
    @ResponseStatus(HttpStatus.FOUND)
    @Operation(
        description = "Отправка данных специалиста",
        tags=["GetMapping","email"],
        externalDocs = ExternalDocumentation(description= "API Documentation",
            url= "https://openweathermap.org/api"),
        summary = "send email about vet",
        hidden = false
    )
    @ApiResponses(
        ApiResponse(responseCode = "200", description = ""),
        ApiResponse(responseCode = "201", description = "error")
    )
    @InternalServerError
    @ResponseOk
    fun sendVet(
        @Parameter(
            name = "title",
            description = "Идентификатор специалиста",
            example = "68406",
            required = true,
            style = ParameterStyle.DEFAULT,
            hidden = false)
        @PathVariable(name = "id") id: Long)=producerService.produceVet(vetComponent.findById(id))

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    @Operation(
        description = "Создание специалиста",
        tags=["PostMapping"],
        externalDocs = ExternalDocumentation(description= "API Documentation",
            url= "https://openweathermap.org/api"),
        summary = "create vet",
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
            description = "Данные специалиста",
            example = """{
                            "fullName": "Test",
                            "address": "Test",
                            "phone": "73-35-2324",
                            "birthday": "1990-01-30",
                            "gender": "MALE",
                            "qualification": "first"
                        }""",
            required = true,
            style = ParameterStyle.DEFAULT)
        @RequestBody vet: Vet) = vetComponent.save(vet)

    @PutMapping("/{id}")
    @ResponseStatus(HttpStatus.OK)
    @Operation(
        description = "Обновление специалиста",
        tags=["PutMapping"],
        externalDocs = ExternalDocumentation(description= "API Documentation",
            url= "https://openweathermap.org/api"),
        summary = "update owner by Id",
        hidden = false
    )
    @Parameters(
            Parameter(name = "id", description = "Идентификатор специалиста",required = true, hidden = false),
            Parameter(name = "vetRequest", description = "Данные специалиста", required = true, hidden = false)
        )
    @ApiResponses(
        ApiResponse(responseCode = "400", description = "Bad Request"),
        ApiResponse(responseCode = "200", description = "OK")
    )
    @InternalServerError
    @ResponseOk
    fun update(
        @Parameter(
            description = "Идентификатор специалиста",
            example = "68406",
            required = true,
            style = ParameterStyle.DEFAULT,
            hidden = false)
        @PathVariable(name = "id") id:Long, @Parameter(
            description = "Данные специалиста",
            example = """{
                            "full_name": "Russ Abbot",
                            "address": "Baltimore",
                            "phone": "73-35-2324",
                            "gender": "MALE",
                            "qualification": "second",
                            "birthday": "1990-01-30"
                        }""",
            required = true,
            style = ParameterStyle.DEFAULT,
            hidden = false)
        @RequestBody vetRequest: VetRequest)
    {
        vetComponent.save(getVet(id ,vetRequest))
    }

    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.OK)
    @Operation(
        description = "Удаление специалиста",
        tags=["DeleteMapping"],
        externalDocs = ExternalDocumentation(description= "API Documentation",
            url= "https://openweathermap.org/api"),
        summary = "delete vet by Id",
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
            description = "Идентификатор специалиста",
            example = "68406",
            required = true,
            style = ParameterStyle.DEFAULT)
        @PathVariable(name = "id") id: Long) = vetComponent.deleteById(id)

    private fun getVet(id:Long ,vetRequest: VetRequest):Vet{
        var vet = Vet(
            id,
            vetRequest.fullName,
            vetRequest.address,
            vetRequest.phone,
            vetRequest.birthday,
            Gender.MALE,
            vetRequest.qualification
        )
        if(vetRequest.gender.equals("FEMALE"))vet.gender = Gender.FEMALE
        return vet
    }
}