package com.example.petclinic.web.annotation

import io.swagger.v3.oas.annotations.media.Content
import io.swagger.v3.oas.annotations.responses.ApiResponse
import org.springframework.http.MediaType

/**
 * @author Grigoriy Zemlyanskiy
 * @version 1.0
 * annotation class ResponseOk
 */
@Target(AnnotationTarget.TYPE, AnnotationTarget.FUNCTION)
@Retention(AnnotationRetention.RUNTIME)
@ApiResponse(
    responseCode = "200",
    description = "data received",
    content = [Content(
        mediaType = MediaType.APPLICATION_JSON_VALUE)]
    )
annotation class ResponseOk