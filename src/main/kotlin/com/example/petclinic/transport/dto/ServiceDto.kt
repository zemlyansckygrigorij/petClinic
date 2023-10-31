package com.example.petclinic.transport.dto

import com.fasterxml.jackson.annotation.JsonAutoDetect
import com.fasterxml.jackson.annotation.JsonProperty

/**
 * @author Grigoriy Zemlyanskiy
 * @version 1.0
 * data class ServiceDto
 */
@JsonAutoDetect(fieldVisibility = JsonAutoDetect.Visibility.ANY)
data class ServiceDto (
    @JsonProperty("name") var name: String,
    @JsonProperty("description") var description: String,
    @JsonProperty("price") var price: Double
)