package com.example.petclinic.transport.dto

import com.fasterxml.jackson.annotation.JsonAutoDetect
import com.fasterxml.jackson.annotation.JsonProperty
import jakarta.persistence.*
import java.util.*

@JsonAutoDetect(fieldVisibility = JsonAutoDetect.Visibility.ANY)
data class VetDto  (
    @JsonProperty("full_name") var fullName: String,
    @JsonProperty("address") var address: String,
    @JsonProperty("phone") var phone: String,
    @JsonProperty("gender") var gender: String,
    @JsonProperty("qualification") var qualification: String?,
    @JsonProperty("birthday") var birthday: String
)

