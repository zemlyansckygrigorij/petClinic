package com.example.petclinic.transport.dto

import com.fasterxml.jackson.annotation.JsonAutoDetect
import com.fasterxml.jackson.annotation.JsonProperty

/**
 * @author Grigoriy Zemlyanskiy
 * @version 1.0
 * data class PetDto
 */
@JsonAutoDetect(fieldVisibility = JsonAutoDetect.Visibility.ANY)
data class PetDto (
    @JsonProperty("name") var name: String,
    @JsonProperty("kind") var kind: String,
    @JsonProperty("age") var age: Int?,
    @JsonProperty("gender") var gender: String,
    @JsonProperty("idOwner") var idOwner: Int?
)
