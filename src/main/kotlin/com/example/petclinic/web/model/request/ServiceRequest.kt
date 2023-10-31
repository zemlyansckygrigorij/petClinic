package com.example.petclinic.web.model.request

import com.example.petclinic.db.entity.Services
import com.fasterxml.jackson.annotation.JsonProperty
import io.swagger.v3.oas.annotations.media.Schema
import jakarta.validation.constraints.NotBlank
import jakarta.validation.constraints.Size

/**
 * @author Grigoriy Zemlyanskiy
 * @version 1.0
 * data class ServiceRequest
 */
@Schema(description = "Создание записи об услуге")
data class ServiceRequest(
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
        fun getServiceRequest(service: Services): ServiceRequest?{
            return service.description?.let {description ->
                ServiceRequest(service.name,
                    description,
                    service.price,
                )
            }
        }
    }
}