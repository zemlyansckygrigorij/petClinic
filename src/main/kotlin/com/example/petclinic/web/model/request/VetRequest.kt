package com.example.petclinic.web.model.request

import com.example.petclinic.db.entity.Vet
import com.fasterxml.jackson.annotation.JsonFormat
import com.fasterxml.jackson.annotation.JsonProperty
import io.swagger.v3.oas.annotations.media.Schema
import jakarta.validation.constraints.NotBlank
import jakarta.validation.constraints.Size
import java.text.SimpleDateFormat
import java.util.*

@Schema(description = "Создание записи о ветеринаре")
data class VetRequest (
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
    @JsonProperty("birthday")
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd")
    val birthday: Date
){
    companion object {
        fun getVetRequest(vet: Vet): VetRequest?{
            return vet.address?.let { address->
                vet.phone?.let { phone ->
                    vet.qualification?.let { qualification ->
                        vet.birthday?.let {birthday ->
                            VetRequest(
                                vet.fullName,
                                address,
                                phone,
                                vet.gender.toString(),
                                qualification,
                                birthday
                            )
                        }
                    }
                }
            }
        }
    }
    override fun toString():String{
        return """{"fullName": "${this.fullName}","address": "${this.address}","phone": "${this.phone}","birthday": "${SimpleDateFormat("yyyy-MM-dd").format(this.birthday)}","qualification": "${this.qualification}","gender": "${this.gender}"}"""
    }
}