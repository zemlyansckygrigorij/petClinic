package com.example.petclinic.transport.dto

import com.fasterxml.jackson.annotation.JsonAutoDetect
import com.fasterxml.jackson.annotation.JsonProperty

@JsonAutoDetect(fieldVisibility = JsonAutoDetect.Visibility.ANY)
data class PetDto (
    @JsonProperty("name") var name: String,
    @JsonProperty("kind") var kind: String,
    @JsonProperty("age") var age: Int?,
    @JsonProperty("gender") var gender: String,
    @JsonProperty("idOwner") var idOwner: Int?
)
