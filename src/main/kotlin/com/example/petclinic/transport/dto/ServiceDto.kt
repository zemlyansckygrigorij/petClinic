package com.example.petclinic.transport.dto

import com.fasterxml.jackson.annotation.JsonAutoDetect
import com.fasterxml.jackson.annotation.JsonProperty

@JsonAutoDetect(fieldVisibility = JsonAutoDetect.Visibility.ANY)
data class ServiceDto (
    @JsonProperty("name") var name: String,
    @JsonProperty("description") var description: String,
    @JsonProperty("price") var price: Double
)