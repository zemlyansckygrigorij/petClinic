package com.example.petclinic.transport.dto

import com.fasterxml.jackson.annotation.JsonAutoDetect
import com.fasterxml.jackson.annotation.JsonProperty

/**
 * @author Grigoriy Zemlyanskiy
 * @version 1.0
 * data class OwnerDto
 */
@JsonAutoDetect(fieldVisibility = JsonAutoDetect.Visibility.ANY)
data class OwnerDto(
    @JsonProperty("full_name") var fullName: String,
    @JsonProperty("address") var address: String,
    @JsonProperty("phone") var phone: String,
    @JsonProperty("gender") var gender: String,
    @JsonProperty("birthday") var birthday: String
)