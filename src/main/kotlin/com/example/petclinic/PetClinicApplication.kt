package com.example.petclinic

import io.swagger.v3.oas.annotations.OpenAPIDefinition
import io.swagger.v3.oas.annotations.info.Info
import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.runApplication
import org.springframework.transaction.annotation.EnableTransactionManagement

@SpringBootApplication
@OpenAPIDefinition(
    info = Info(title = "Документация для работы с контроллерами приложения petClinic",
        version = "Swagger 3.0",
        description = "Общая информация "))
@EnableTransactionManagement
class PetClinicApplication

fun main(args: Array<String>) {
    runApplication<PetClinicApplication>(*args)
}
