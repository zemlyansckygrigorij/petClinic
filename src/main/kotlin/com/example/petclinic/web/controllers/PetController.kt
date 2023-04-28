package com.example.petclinic.web.controllers

import com.example.petclinic.db.entity.Gender
import com.example.petclinic.db.entity.Pet
import com.example.petclinic.db.services.PetComponent
import com.example.petclinic.transport.service.ProducerService
import com.example.petclinic.web.model.request.PetRequest
import com.example.petclinic.web.model.response.PetResponse
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

@RestController
@RequestMapping("/pet")
@Tag(
    name = "API работы с животных",
    description = "Api work with animal",
    externalDocs = ExternalDocumentation(description= "API Documentation",
        url= "https://openweathermap.org/api")
)
class PetController(private val petComponent: PetComponent,
                    private val producerService: ProducerService
) {

    @GetMapping
    @ResponseStatus(HttpStatus.FOUND)
    @Operation(
        description = "Получение всех животных",
        summary = "Retrieve all pets",
        tags = ["GetMapping", "All" ],
        externalDocs = ExternalDocumentation(description= "API Documentation",
            url= "https://openweathermap.org/api"),
        hidden = false)
    @ApiResponse(responseCode = "200", description = "list of PetResponse")
    fun findAll() = petComponent.findAll().map { pet -> PetResponse.getPetResponse(pet) }

    @GetMapping("/{id}")
    @ResponseStatus(HttpStatus.FOUND)
    @Operation(
        description = "Получение животного по идентификатору",
        summary = "Retrieve a pet by Id",
        tags = ["GetMapping", "id" ],
        externalDocs = ExternalDocumentation(description= "API Documentation",
            url= "https://openweathermap.org/api"),
        hidden = false)
    @ApiResponses(*[
        ApiResponse(responseCode = "200", description = "PetResponse"),
        ApiResponse(responseCode = "201", description = "error")
    ])
    fun findById(
        @Parameter(
            description = "Идентификатор животного",
            example = "68406",
            required = true,
            style = ParameterStyle.DEFAULT,
            hidden = false)
        @PathVariable(name = "id") id: Long) = PetResponse
        .getPetResponse(petComponent.findById(id))

    @GetMapping("/name")
    @ResponseStatus(HttpStatus.FOUND)
    @Operation(
        description = "Получение всех списка животных по имени ",
        summary = "get pets by name",
        tags = ["GetMapping","name"],
        externalDocs = ExternalDocumentation(description= "API Documentation",
            url= "https://openweathermap.org/api"),
        hidden = false
    )
    @ApiResponses(*[
        ApiResponse(responseCode = "200", description = "list of PetResponse"),
        ApiResponse(responseCode = "201", description = "error")
    ])
    fun findByName(
        @Parameter(
            description = "Имя животного",
            example = "Барбос",
            required = true,
            style = ParameterStyle.DEFAULT,
            hidden = false)
        @RequestBody name: String) = petComponent
        .findByName(name)
        .map { pet -> PetResponse.getPetResponse(pet) }


    @GetMapping("/owner/{id}")
    @ResponseStatus(HttpStatus.FOUND)
    @Operation(
        description = "Получение списка животных по идентификатору владельца",
        summary = "Retrieve a pets by Id",
        tags = ["GetMapping", "All" ],
        externalDocs = ExternalDocumentation(description= "API Documentation",
            url= "https://openweathermap.org/api"),
        hidden = false)
    @ApiResponses(*[
        ApiResponse(responseCode = "200", description = "list of PetResponse"),
        ApiResponse(responseCode = "201", description = "error")
    ])
    fun findByOwnerId(@PathVariable(name = "id") id: Long) =petComponent
        .findByOwnerId(id)
        .map { pet -> PetResponse.getPetResponse(pet) }

    @GetMapping("/{id}/send")
    @ResponseStatus(HttpStatus.FOUND)
    @Operation(
        description = "Отправка данных животного",
        tags=["GetMapping","email"],
        externalDocs = ExternalDocumentation(description= "API Documentation",
            url= "https://openweathermap.org/api"),
        summary = "send email about pet",
        hidden = false
    )
    @ApiResponses(*[
        ApiResponse(responseCode = "200", description = ""),
        ApiResponse(responseCode = "201", description = "error")
    ])
    fun sendPet(
        @Parameter(
            name = "title",
            description = "Идентификатор животного",
            example = "68406",
            required = true,
            style = ParameterStyle.DEFAULT,
            hidden = false)
        @PathVariable(name = "id") id: Long)=producerService.producePet(petComponent.findById(id))

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    @Operation(
        description = "Создание животного",
        tags=["PostMapping"],
        externalDocs = ExternalDocumentation(description= "API Documentation",
            url= "https://openweathermap.org/api"),
        summary = "create pet",
        hidden = false
    )
    @ApiResponses(*[
        ApiResponse(responseCode = "200", description = "Owner"),
        ApiResponse(responseCode = "201", description = "error")
    ])
    fun create(
        @Parameter(
            description = "Данные животного",
            example = """{"kind":"CAT","name":"test1","age":1,"idOwner":1,"gender":"MALE"}""",
            required = true,
            style = ParameterStyle.DEFAULT)
        @RequestBody pet: Pet): Pet = petComponent.save(pet)

    @PutMapping("/{id}")
    @ResponseStatus(HttpStatus.OK)
    @Operation(
        description = "Обновление животного",
        tags=["PutMapping"],
        externalDocs = ExternalDocumentation(description= "API Documentation",
            url= "https://openweathermap.org/api"),
        summary = "update owner by Id",
        hidden = false
    )
    @Parameters(
        *[
            Parameter(name = "id", description = "Идентификатор животного",required = true, hidden = false),
            Parameter(name = "petRequest", description = "Данные животного", required = true, hidden = false)
        ])
    @ApiResponses(*[
        ApiResponse(responseCode = "200", description = ""),
        ApiResponse(responseCode = "201", description = "error")
    ])
    fun update(
        @Parameter(
            description = "Идентификатор животного",
            example = "68406",
            required = true,
            style = ParameterStyle.DEFAULT,
            hidden = false)
        @PathVariable(name = "id") id: Long,

        @Parameter(
            description = "Данные животного",
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
        @RequestBody petRequest: PetRequest)
    {
        var petFromTable = petComponent.findById(id)
        if(petRequest.gender.equals("MALE")) petFromTable.gender = Gender.MALE
        if(petRequest.gender.equals("FEMALE")) petFromTable.gender = Gender.FEMALE
        petFromTable.name = petRequest.name
        petFromTable.age =petRequest.age
        petFromTable.kind = petRequest.kind
        petFromTable.idOwner = petRequest.idOwner
        petComponent.save(petFromTable)
    }

    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.OK)
    @Operation(
        description = "Удаление животного",
        tags=["DeleteMapping"],
        externalDocs = ExternalDocumentation(description= "API Documentation",
            url= "https://openweathermap.org/api"),
        summary = "delete pet by Id",
        hidden = false
    )
    @ApiResponses(*[
        ApiResponse(responseCode = "200", description = ""),
        ApiResponse(responseCode = "201", description = "error")
    ])
    fun delete(
        @Parameter(
            description = "Идентификатор животного",
            example = "68406",
            required = true,
            style = ParameterStyle.DEFAULT)
        @PathVariable(name = "id") id: Long) = petComponent.deleteById(id)
}