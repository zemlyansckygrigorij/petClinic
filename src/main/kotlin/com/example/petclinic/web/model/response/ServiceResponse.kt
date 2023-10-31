package com.example.petclinic.web.model.response

import com.example.petclinic.db.entity.Services
import com.fasterxml.jackson.annotation.JsonProperty
import io.swagger.v3.oas.annotations.media.Schema
import jakarta.validation.constraints.NotBlank
import jakarta.validation.constraints.Size

/**
 * @author Grigoriy Zemlyanskiy
 * @version 1.0
 * data class ServiceResponse
 */
@Schema(description = "Услуга")
data class ServiceResponse (
    @Schema(
        accessMode = Schema.AccessMode.READ_ONLY,
        description = "Название",
        example = "Прививка",
        required = true
    )
    @NotBlank
    @Size(max = 100)
    @JsonProperty("name") var name: String,

    @Schema(
        accessMode = Schema.AccessMode.READ_ONLY,
        description = "Описание",
        example = "Вакцинация от клеща",
        required = true
    )
    @NotBlank
    @Size(max = 100)
    @JsonProperty("description") var description: String,

    @Schema(
        accessMode = Schema.AccessMode.READ_ONLY,
        description = "Цена",
        example = "23.4",
        required = true
    )
    @NotBlank
    @Size(max = 100)
    @JsonProperty("price") var price: Double

){
    companion object {
        fun getServiceResponse(service: Services): ServiceResponse?{
            return service.description?.let {description ->
                ServiceResponse(service.name,
                    description,
                    service.price,
                )
            }
        }
    }
}