package com.example.petclinic.logging

/**
 * @author Grigoriy Zemlyanskiy
 * @version 1.0
 * annotation class TransactionLogging
 */
@Target(AnnotationTarget.TYPE, AnnotationTarget.FUNCTION)
@Retention(AnnotationRetention.RUNTIME)
annotation class TransactionLogging()
