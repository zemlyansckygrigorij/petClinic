package com.example.petclinic.web.exceptions

import org.springframework.beans.factory.annotation.Value
import org.springframework.context.annotation.PropertySource
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.ControllerAdvice
import org.springframework.web.bind.annotation.ExceptionHandler

@PropertySource("classpath:error.messages.properties")
@ControllerAdvice
class ExceptionControllerAdvice {

    @Value("\${OwnerNotFoundException.message}")
    lateinit var ownerNotFoundExceptionMessage:String

    @Value("\${ListOfOwnersNotFoundException.message}")
    lateinit var listOfOwnersNotFoundExceptionMessage:String

    @Value("\${PetNotFoundException.message}")
    lateinit var petNotFoundExceptionMessage:String

    @Value("\${ListOfPetsNotFoundException.message}")
    lateinit var listOfPetsNotFoundMessage:String
    @Value("\${ServiceNotFoundException.message}")
    lateinit var serviceNotFoundMessage:String

    @Value("\${ListOfServicesNotFoundException.message}")
    lateinit var listOfServicesNotFoundMessage:String

    @Value("\${VetNotFoundException.message}")
    lateinit var vetNotFoundExceptionMessage:String

    @Value("\${ListOfVetsNotFoundException.message}")
    lateinit var listOfVetsNotFoundMessage:String
    @ExceptionHandler
    fun handleIllegalStateException(ex: IllegalStateException): ResponseEntity<ErrorMessageModel> {

        val errorMessage = ErrorMessageModel(
            HttpStatus.NOT_FOUND.value(),
            ex.message
        )
        return ResponseEntity(errorMessage, HttpStatus.BAD_REQUEST)
    }

    @ExceptionHandler
    fun handleListOfOwnersNotFoundException(ex: ListOfOwnersNotFoundException): ResponseEntity<ErrorMessageModel> {
        val errorMessage = ErrorMessageModel(
            HttpStatus.NOT_FOUND.value(),
            listOfOwnersNotFoundExceptionMessage
        )
        return ResponseEntity(errorMessage, HttpStatus.NOT_FOUND)
    }
    @ExceptionHandler
    fun handleListOfPetsNotFoundException(ex: ListOfPetsNotFoundException): ResponseEntity<ErrorMessageModel> {
        val errorMessage = ErrorMessageModel(
            HttpStatus.NOT_FOUND.value(),
            listOfPetsNotFoundMessage
        )
        return ResponseEntity(errorMessage, HttpStatus.NOT_FOUND)
    }
    @ExceptionHandler
    fun handleListOfServicesNotFoundException(ex: ListOfServicesNotFoundException): ResponseEntity<ErrorMessageModel> {
        val errorMessage = ErrorMessageModel(
            HttpStatus.NOT_FOUND.value(),
            listOfServicesNotFoundMessage
        )
        return ResponseEntity(errorMessage, HttpStatus.NOT_FOUND)
    }
    @ExceptionHandler
    fun handleListOfVetsNotFoundException(ex: ListOfVetsNotFoundException): ResponseEntity<ErrorMessageModel> {
        val errorMessage = ErrorMessageModel(
            HttpStatus.NOT_FOUND.value(),
            listOfVetsNotFoundMessage
        )
        return ResponseEntity(errorMessage, HttpStatus.NOT_FOUND)
    }

    @ExceptionHandler
    fun handleOwnerNotFoundException(ex: OwnerNotFoundException): ResponseEntity<ErrorMessageModel> {
        val errorMessage = ErrorMessageModel(
            HttpStatus.NOT_FOUND.value(),
            ownerNotFoundExceptionMessage
        )
        return ResponseEntity(errorMessage, HttpStatus.NOT_FOUND)
    }
    @ExceptionHandler
    fun handlePetNotFoundException(ex: PetNotFoundException): ResponseEntity<ErrorMessageModel> {
        val errorMessage = ErrorMessageModel(
            HttpStatus.NOT_FOUND.value(),
            petNotFoundExceptionMessage
        )
        return ResponseEntity(errorMessage, HttpStatus.NOT_FOUND)
    }
    @ExceptionHandler
    fun handleServiceNotFoundException(ex: ServiceNotFoundException): ResponseEntity<ErrorMessageModel> {
        val errorMessage = ErrorMessageModel(
            HttpStatus.NOT_FOUND.value(),
            serviceNotFoundMessage
        )
        return ResponseEntity(errorMessage, HttpStatus.NOT_FOUND)
    }
    @ExceptionHandler
    fun handleVetNotFoundException(ex: VetNotFoundException): ResponseEntity<ErrorMessageModel> {
        val errorMessage = ErrorMessageModel(
            HttpStatus.NOT_FOUND.value(),
            vetNotFoundExceptionMessage
        )
        return ResponseEntity(errorMessage, HttpStatus.NOT_FOUND)
    }
}