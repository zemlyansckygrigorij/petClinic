package com.example.petclinic.web.model.response

import com.example.petclinic.db.entity.Services
import com.fasterxml.jackson.annotation.JsonProperty
import io.swagger.v3.oas.annotations.media.Schema
import jakarta.validation.constraints.NotBlank
import jakarta.validation.constraints.Size

@Schema(description = "Услуга")
data class ServiceResponse (
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
            fun getServiceResponse(service: Services): ServiceResponse?{
                return service.description?.let {it
                    ServiceResponse(service.name,
                        it,
                        service.price,
                    )
                }
            }
        }
    }