package com.example.petclinic.logging

import lombok.RequiredArgsConstructor
import lombok.extern.slf4j.Slf4j
import org.aspectj.lang.JoinPoint
import org.aspectj.lang.annotation.AfterThrowing
import org.aspectj.lang.annotation.Aspect
import org.aspectj.lang.annotation.Before
import org.slf4j.LoggerFactory
import org.springframework.stereotype.Component
import java.time.LocalDateTime
import java.time.format.DateTimeFormatter



@Slf4j
@Aspect
@Component
@RequiredArgsConstructor
class TransactionLogger(){
    companion object {
        private val logger = LoggerFactory.getLogger(javaClass)
    }

    @Before("@annotation(com.example.petclinic.logging.TransactionLogging))")
    fun logBeforeTransactionExecution(joinPoint: JoinPoint?) {
        val formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm")
        val current = LocalDateTime.now().format(formatter)
            logger.info("Transaction - "+  joinPoint?.let { getJoinPointName(it) }+" - "+ current)
    }
    @AfterThrowing("@annotation(com.example.petclinic.logging.TransactionLogging))")
    fun logAfterThrowingTransactionExecution(joinPoint: JoinPoint?) {
        val formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm")
        val current = LocalDateTime.now().format(formatter)
        logger.warn("TransactionWarn - "+  joinPoint?.let { getJoinPointName(it) }+" - "+ current)
    }
    fun getJoinPointName(joinPoint: JoinPoint):String {
        return "class: "+joinPoint.target.javaClass.name.toString()+
                " - method: "+ joinPoint.getSignature().toShortString()+
                " - parameters: "+joinPoint.args.map { it.toString() }.toList()
    }
}
