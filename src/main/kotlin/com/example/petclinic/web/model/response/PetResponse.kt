package com.example.petclinic.web.model.response

import com.example.petclinic.db.entity.Pet
import com.fasterxml.jackson.annotation.JsonProperty
import io.swagger.v3.oas.annotations.media.Schema
import jakarta.validation.constraints.NotBlank
import jakarta.validation.constraints.Size

/**
 * @author Grigoriy Zemlyanskiy
 * @version 1.0
 * data class PetResponse
 */
@Schema(description = "Питомец")
data class PetResponse (
    @Schema(
        accessMode = Schema.AccessMode.READ_ONLY,
        description = "Кличка",
        example = "Барбос",
        required = true
    )
    @NotBlank
    @Size(max = 100)
    @JsonProperty("name") var name: String,

    @Schema(
        accessMode = Schema.AccessMode.READ_ONLY,
        description = "Вид",
        example = "Собака",
        required = true
    )
    @NotBlank
    @Size(max = 100)
    @JsonProperty("kind") var kind: String,

    @Schema(
        accessMode = Schema.AccessMode.READ_ONLY,
        description = "Возраст",
        example = "14",
        required = true
    )
    @NotBlank
    @Size(max = 100)
    @JsonProperty("age") var age: Int?,

    @Schema(
        accessMode = Schema.AccessMode.READ_ONLY,
        description = "Пол",
        example = "Самец",
        required = true
    )
    @NotBlank
    @Size(max = 100)
    @JsonProperty("gender") var gender: String,

    @Schema(
        accessMode = Schema.AccessMode.READ_ONLY,
        description = "идентификатор владельца",
        example = "123",
        required = true
    )
    @NotBlank
    @Size(max = 100)
    @JsonProperty("idOwner") var idOwner: Int?
) {
    companion object {
        fun getPetResponse(pet: Pet): PetResponse? {
            return pet.name?.let {name ->
                PetResponse(
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