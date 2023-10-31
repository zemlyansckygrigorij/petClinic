package com.example.petclinic.web.controllers

import com.example.petclinic.db.entity.Services
import com.example.petclinic.db.services.ServiceComponent
import com.example.petclinic.transport.service.ProducerService
import com.example.petclinic.web.annotation.InternalServerError
import com.example.petclinic.web.annotation.ResponseOk
import com.example.petclinic.web.model.request.ServiceRequest
import com.example.petclinic.web.model.response.ServiceResponse
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
 * class ServiceController
 */
@RestController
@RequestMapping("/service")
@Tag(
    name = "API работы с услугами",
    description = "Api work with services",
    externalDocs = ExternalDocumentation(description= "API Documentation",
        url= "https://openweathermap.org/api")
)
class ServiceController(private val serviceComponent:ServiceComponent,
                        private val producerService: ProducerService
) {

    @GetMapping
    @ResponseStatus(HttpStatus.FOUND)
    @Operation(
        description = "Получение всех услуг",
        summary = "Retrieve all services",
        tags = ["GetMapping", "All" ],
        externalDocs = ExternalDocumentation(description= "API Documentation",
            url= "https://openweathermap.org/api"),
        hidden = false
    )
    @ApiResponse(responseCode = "200", description = "list of ServiceResponse")
    @InternalServerError
    @ResponseOk
    fun findAll() = serviceComponent.findAll().map { service -> ServiceResponse
        .getServiceResponse(service)}

    @GetMapping("/{id}")
    @ResponseStatus(HttpStatus.FOUND)
    @Operation(
        description = "Получение услуги по идентификатору",
        summary = "Retrieve a service by Id",
        tags = ["GetMapping", "id" ],
        externalDocs = ExternalDocumentation(description= "API Documentation",
            url= "https://openweathermap.org/api"),
        hidden = false)
    @ApiResponses(
        ApiResponse(responseCode = "200", description = "ServiceResponse"),
        ApiResponse(responseCode = "204", description = "No Content")
    )
    @InternalServerError
    @ResponseOk
    fun findById(
        @Parameter(
            description = "Идентификатор услуги",
            example = "68406",
            required = true,
            style = ParameterStyle.DEFAULT,
            hidden = false)
        @PathVariable(name = "id") id:Long)= ServiceResponse
        .getServiceResponse(serviceComponent.findById(id))

    @GetMapping("/name")
    @ResponseStatus(HttpStatus.FOUND)
    @Operation(
        description = "Получение списка услуг по имени",
        summary = "get services by name",
        tags = ["GetMapping","name"],
        externalDocs = ExternalDocumentation(description= "API Documentation",
            url= "https://openweathermap.org/api"),
        hidden = false
    )
    @ApiResponses(
        ApiResponse(responseCode = "200", description = "list of ServiceResponse"),
        ApiResponse(responseCode = "204", description = "No Content")
    )
    @InternalServerError
    @ResponseOk
    fun findByName(
        @Parameter(
            description = "Название услуги",
            example = "вакцинация",
            required = true,
            style = ParameterStyle.DEFAULT,
            hidden = false)
        @RequestBody name:String)= serviceComponent.findByName(name).map { service -> ServiceResponse
        .getServiceResponse(service)}

    @GetMapping("/{id}/send")
    @ResponseStatus(HttpStatus.FOUND)
    @Operation(
        description = "Отправка данных услуги",
        tags=["GetMapping","email"],
        externalDocs = ExternalDocumentation(description= "API Documentation",
            url= "https://openweathermap.org/api"),
        summary = "send email about service",
        hidden = false
    )
    @ApiResponses(
        ApiResponse(responseCode = "200", description = ""),
        ApiResponse(responseCode = "500", description = "Internal Server Error")
    )
    @InternalServerError
    @ResponseOk
    fun sendService(
        @Parameter(
            name = "title",
            description = "Идентификатор услуги",
            example = "68406",
            required = true,
            style = ParameterStyle.DEFAULT,
            hidden = false)
        @PathVariable(name = "id") id: Long)=producerService.produceService(serviceComponent.findById(id))

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    @Operation(
        description = "Создание услуги",
        tags=["PostMapping"],
        externalDocs = ExternalDocumentation(description= "API Documentation",
            url= "https://openweathermap.org/api"),
        summary = "create service",
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
            description = "Данные услуги",
            example = """{
                            "name": "Test",
                            "description": "Test",
                            "price": 10.1
                         }""",
            required = true,
            style = ParameterStyle.DEFAULT)
        @RequestBody service: Services)=serviceComponent.save(service)

    @PutMapping("/{id}")
    @ResponseStatus(HttpStatus.OK)
    @Operation(
        description = "Обновление услуги",
        tags=["PutMapping"],
        externalDocs = ExternalDocumentation(description= "API Documentation",
            url= "https://openweathermap.org/api"),
        summary = "update owner by Id",
        hidden = false
    )
    @Parameters(
            Parameter(name = "id", description = "Идентификатор услуги",required = true, hidden = false),
            Parameter(name = "serviceRequest", description = "Данные  услуги", required = true, hidden = false)
        )
    @ApiResponses(
        ApiResponse(responseCode = "400", description = "Bad Request"),
        ApiResponse(responseCode = "200", description = "OK")
    )
    @InternalServerError
    @ResponseOk
    fun save(
        @Parameter(
            description = "Идентификатор услуги",
            example = "68406",
            required = true,
            style = ParameterStyle.DEFAULT,
            hidden = false)
        @PathVariable(name = "id") id: Long,

        @Parameter(
            description = "Данные услуги",
            example = """{
                            "name": "Pet Wellness Exams",
                            "description": "Pet Wellness Exams are a key component of your pet’s preventative care plan and long-term health and happiness. An annual wellness visit is a checkup for your pet that includes a nose-to-tail physical examination and a discussion of your pet’s lifestyle and home environment. Your veterinarian will also ask questions about your pet’s nutrition and behavior and address any concerns that you may have. Your veterinarian may recommend additional diagnostic testing and vaccinations (for an additional cost).",
                            "price": 15.1
                        }""",
            required = true,
            style = ParameterStyle.DEFAULT,
            hidden = false)
        @RequestBody serviceRequest: ServiceRequest)
    {
        val serviceFromTable = serviceComponent.findById(id)
        serviceFromTable.name = serviceRequest.name
        serviceFromTable.description =serviceRequest.description
        serviceFromTable.price = serviceRequest.price
        serviceComponent.save(serviceFromTable)
    }


    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.OK)
    @Operation(
        description = "Удаление услуги",
        tags=["DeleteMapping"],
        externalDocs = ExternalDocumentation(description= "API Documentation",
            url= "https://openweathermap.org/api"),
        summary = "delete service by Id",
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
            description = "Идентификатор услуги",
            example = "68406",
            required = true,
            style = ParameterStyle.DEFAULT)
        @PathVariable(name = "id") id:Long) = serviceComponent.deleteById(id)
}