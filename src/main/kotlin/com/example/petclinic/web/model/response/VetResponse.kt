package com.example.petclinic.web.model.response

import com.example.petclinic.db.entity.Vet
import com.fasterxml.jackson.annotation.JsonProperty
import io.swagger.v3.oas.annotations.media.Schema
import jakarta.validation.constraints.NotBlank
import jakarta.validation.constraints.Size

/**
 * @author Grigoriy Zemlyanskiy
 * @version 1.0
 * data class VetResponse
 */
@Schema(description = "Специалист")
data class VetResponse (
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
        accessMode = Schema.AccessMode.READ_ONLY,
        description = "Квалификация",
        example = "Высшая",
        required = true
    )
    @NotBlank
    @Size(max = 100)
    @JsonProperty("qualification") var qualification: String,

    @Schema(
        accessMode = Schema.AccessMode.READ_ONLY,
        description = "День Рождения",
        example = "1990-01-30",
        required = true
    )
    @NotBlank
    @Size(max = 100)
    @JsonProperty("birthday") var birthday: String
){
    companion object {
        fun getVetResponse(vet: Vet): VetResponse?{
            return vet.address?.let { address->
                vet.phone?.let { phone ->
                    vet.qualification?.let { qualification ->
                        VetResponse(
                            vet.fullName,
                            address,
                            phone,
                            vet.gender.toString(),
                            qualification,
                            vet.birthday.toString()
                        )
                    }
                }
            }
        }
    }
}