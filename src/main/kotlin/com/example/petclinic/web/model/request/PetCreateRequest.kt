package com.example.petclinic.web.model.request

import com.example.petclinic.db.entity.Pet
import com.fasterxml.jackson.annotation.JsonProperty
import io.swagger.v3.oas.annotations.media.Schema
import jakarta.validation.constraints.NotBlank
import jakarta.validation.constraints.Size

@Schema(description = "Создание записи о питомце")
data class PetCreateRequest (
    @Schema(description = "Кличка", example = "Барбос")
    @NotBlank
    @Size(max = 100)
    @JsonProperty("name") var name: String,

    @Schema(description = "Вид", example = "Собака")
    @NotBlank
    @Size(max = 100)
    @JsonProperty("kind") var kind: String,

    @Schema(description = "Возраст", example = "14")
    @NotBlank
    @Size(max = 100)
    @JsonProperty("age") var age: Int?,

    @Schema(description = "Пол", example = "Самец")
    @NotBlank
    @Size(max = 100)
    @JsonProperty("gender") var gender: String,

    @Schema(description = "идентификатор владельца", example = "123")
    @NotBlank
    @Size(max = 100)
    @JsonProperty("idOwner") var idOwner: Int?
) {
    companion object {
        fun getPetCreateRequest (pet: Pet): PetCreateRequest ? {
            return pet.name?.let {name ->
                PetCreateRequest (
                    name,
                    pet.kind,
                    pet.age,
                    pet.gender.toString(),
                    pet.idOwner
                )
            }
        }
    }
}