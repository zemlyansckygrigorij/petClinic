package com.example.petclinic.web.model.request

import com.example.petclinic.db.entity.Vet
import com.fasterxml.jackson.annotation.JsonProperty
import io.swagger.v3.oas.annotations.media.Schema
import jakarta.validation.constraints.NotBlank
import jakarta.validation.constraints.Size

@Schema(description = "Создание записи о ветеринаре")
data class VetCreateRequest  (
    @Schema(description = "ФИО", example = "Эйлер Леонард Мартинович")
    @NotBlank
    @Size(max = 100)
    @JsonProperty("full_name") var fullName: String,

    @Schema(description = "Адрес", example = "Москва, Шаболовка 36")
    @NotBlank
    @Size(max = 100)
    @JsonProperty("address") var address: String,

    @Schema(description = "Телефон", example = "89313450202")
    @NotBlank
    @Size(max = 100)
    @JsonProperty("phone") var phone: String,

    @Schema(description = "Пол", example = "Мужской")
    @NotBlank
    @Size(max = 100)
    @JsonProperty("gender") var gender: String,

    @Schema(description = "Квалификация", example = "Высшая")
    @NotBlank
    @Size(max = 100)
    @JsonProperty("qualification") var qualification: String,

    @Schema(description = "День Рождения", example = "1990-01-30")
    @NotBlank
    @Size(max = 100)
    @JsonProperty("birthday") var birthday: String
){
    companion object {
        fun getVetCreateRequest(vet: Vet): VetCreateRequest?{
            return vet.address?.let { address->
                vet.phone?.let { phone ->
                    vet.qualification?.let { qualification ->
                        VetCreateRequest(
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