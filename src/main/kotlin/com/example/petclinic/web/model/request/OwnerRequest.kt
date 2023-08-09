package com.example.petclinic.web.model.request

import com.example.petclinic.db.entity.Owner
import com.fasterxml.jackson.annotation.JsonFormat
import com.fasterxml.jackson.annotation.JsonProperty
import io.swagger.v3.oas.annotations.media.Schema
import jakarta.validation.constraints.NotBlank
import jakarta.validation.constraints.Size
import java.text.SimpleDateFormat
import java.util.*


@Schema(description = "Создание записи о собственнике")
data class OwnerRequest(
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

    @Schema(description = "День Рождения", example = "1990-01-30")
    @NotBlank
    @Size(max = 100)
    @JsonProperty("birthday")
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd")
    val birthday: Date
){
    companion object {
        fun getOwnerRequest(owner: Owner): OwnerRequest {
            return OwnerRequest(
                owner.fullName,
                owner.address,
                owner.phone,
                owner.gender.toString(),
                owner.birthday)
        }
    }
    override fun toString():String{
        val dateFormat = SimpleDateFormat("yyyy-MM-dd")
        return """{"fullName": "${this.fullName}","address": "${this.address}","phone": "${this.phone}","birthday": "${dateFormat.format(this.birthday)}","gender": "${this.gender}"}"""
    }
}
