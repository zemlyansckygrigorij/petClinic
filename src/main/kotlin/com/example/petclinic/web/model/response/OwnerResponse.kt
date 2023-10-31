package com.example.petclinic.web.model.response

import com.example.petclinic.db.entity.Owner
import com.fasterxml.jackson.annotation.JsonProperty
import io.swagger.v3.oas.annotations.media.Schema
import jakarta.validation.constraints.NotBlank
import jakarta.validation.constraints.Size

/**
 * @author Grigoriy Zemlyanskiy
 * @version 1.0
 * data class OwnerResponse
 */
@Schema(description = "Собственник")
data class OwnerResponse(
    @Schema(
        accessMode = Schema.AccessMode.READ_ONLY,
        description = "ФИО",
        example = "Эйлер Леонард Мартинович",
        required = true
    )
    @NotBlank
    @Size(max = 100)
    @JsonProperty("full_name") var fullName: String,

    @Schema(
        accessMode = Schema.AccessMode.READ_ONLY,
        description = "Адрес",
        example = "Москва, Шаболовка 36",
        required = true
    )
    @NotBlank
    @Size(max = 100)
    @JsonProperty("address") var address: String,

    @Schema(
        accessMode = Schema.AccessMode.READ_ONLY,
        description = "Телефон",
        example = "89313450202",
        required = true
    )
    @NotBlank
    @Size(max = 100)
    @JsonProperty("phone") var phone: String,

    @Schema(
        accessMode = Schema.AccessMode.READ_ONLY,
        description = "Пол",
        example = "Мужской",
        required = true
    )
    @NotBlank
    @Size(max = 100)
    @JsonProperty("gender") var gender: String,

    @Schema(
        additionalProperties = Schema.AdditionalPropertiesValue.TRUE,
        requiredMode = Schema.RequiredMode.REQUIRED,
        description = "День Рождения",
        example = "1990-01-30",
        required = true)
    @NotBlank
    @Size(max = 100)
    @JsonProperty("birthday") var birthday: String
){
    companion object {
        fun getOwnerResponse(owner: Owner): OwnerResponse{
            return OwnerResponse(
                owner.fullName,
                owner.address,
                owner.phone,
                owner.gender.toString(),
                owner.birthday.toString())
        }
    }
}