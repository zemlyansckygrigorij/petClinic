package com.example.petclinic.web.model.request

import com.example.petclinic.db.entity.Services
import com.fasterxml.jackson.annotation.JsonProperty
import io.swagger.v3.oas.annotations.media.Schema
import jakarta.validation.constraints.NotBlank
import jakarta.validation.constraints.Size

@Schema(description = "Обновление записи об услуге")
data class ServiceUpdateRequest (
    @Schema(description = "Идентификатор собственника", example = "1")
    @Size(max = 100)
    @JsonProperty("id") var id : Long,


    @Schema(description = "Название", example = "Прививка")
    @NotBlank
    @Size(max = 100)
    @JsonProperty("name") var name: String,

    @Schema(description = "Описание", example = "Вакцинация от клеща")
    @NotBlank
    @Size(max = 100)
    @JsonProperty("description") var description: String,

    @Schema(description = "Цена", example = "23.4")
    @NotBlank
    @Size(max = 100)
    @JsonProperty("price") var price: Double

){
    companion object {
        fun getServiceUpdateRequest(service: Services): ServiceUpdateRequest?{
            return service.description?.let {description ->
                ServiceUpdateRequest(
                    service.id,
                    service.name,
                    description,
                    service.price,
                )
            }
        }
    }
}