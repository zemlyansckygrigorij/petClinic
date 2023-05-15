package com.example.petclinic.db.services

import com.example.petclinic.db.entity.Services
import com.example.petclinic.db.repo.ServiceRepo
import com.example.petclinic.logging.TransactionLogging
import com.example.petclinic.web.exceptions.ListOfServicesNotFoundException
import com.example.petclinic.web.exceptions.ServiceNotFoundException
import jakarta.persistence.LockModeType
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.data.jpa.repository.Lock
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Isolation
import org.springframework.transaction.annotation.Propagation
import org.springframework.transaction.annotation.Transactional
import java.io.IOException
import java.lang.RuntimeException
import java.sql.SQLException

/**
 * @author Grigoriy Zemlyanskiy
 * @version 1.0
 * class ServiceComponentImpl
 */
@Service
class ServiceComponentImpl @Autowired constructor(
    val serviceRepo: ServiceRepo
): ServiceComponent {

    @TransactionLogging
    @Lock(LockModeType.PESSIMISTIC_WRITE)
    @Transactional(
        isolation = Isolation.SERIALIZABLE,
        label = ["label"],
        readOnly = false,
        timeout = 10,
        propagation = Propagation.REQUIRED,
        rollbackFor =(arrayOf(RuntimeException::class, IOException::class)),
        noRollbackFor = (arrayOf(SQLException::class)),
        transactionManager ="transactionManager",
        value = ""
    )
    override fun save(service: Services): Services {
        return serviceRepo.save(service)
    }

    @TransactionLogging
    @Lock(LockModeType.OPTIMISTIC)
    @Transactional(
        isolation = Isolation.READ_COMMITTED,
        label = ["label"],
        readOnly = true,
        timeout = 10,
        propagation = Propagation.SUPPORTS,
        rollbackFor =(arrayOf(RuntimeException::class,IOException::class)),
        noRollbackFor = (arrayOf(SQLException::class)),
        transactionManager ="transactionManager",
        value = "")
    override fun findById(id: Long): Services {
        return serviceRepo.findById(id).orElseThrow { throw  ServiceNotFoundException() }
    }

    @TransactionLogging
    @Lock(LockModeType.OPTIMISTIC)
    @Transactional(
        isolation = Isolation.READ_COMMITTED,
        label = ["label"],
        readOnly = true,
        timeout = 10,
        propagation = Propagation.SUPPORTS,
        rollbackFor =(arrayOf(RuntimeException::class,IOException::class)),
        noRollbackFor = (arrayOf(SQLException::class)),
        transactionManager ="transactionManager",
        value = "")
    override fun findAll(): ArrayList<Services> {
        val serviceList = ArrayList<Services>()
        if(serviceList.addAll( serviceRepo.findAll().toList())){
            return serviceList
        }else{
            throw ListOfServicesNotFoundException()
        }
    }

    @TransactionLogging
    @Lock(LockModeType.PESSIMISTIC_WRITE)
    @Transactional(
        isolation = Isolation.SERIALIZABLE,
        label = ["label"],
        readOnly = false,
        timeout = 10,
        propagation = Propagation.REQUIRED,
        rollbackFor =(arrayOf(RuntimeException::class,IOException::class)),
        noRollbackFor = (arrayOf(SQLException::class)),
        transactionManager ="transactionManager",
        value = ""
    )
    override fun deleteById(id: Long) {
        serviceRepo.findById(id).orElseThrow { throw  ServiceNotFoundException() }
        serviceRepo.deleteById(id)
    }

    @TransactionLogging
    @Lock(LockModeType.OPTIMISTIC)
    @Transactional(
        isolation = Isolation.READ_COMMITTED,
        label = ["label"],
        readOnly = true,
        timeout = 10,
        propagation = Propagation.SUPPORTS,
        rollbackFor =(arrayOf(RuntimeException::class,IOException::class)),
        noRollbackFor = (arrayOf(SQLException::class)),
        transactionManager ="transactionManager",
        value = "")
    override fun findByName(name: String): ArrayList<Services> {
        return serviceRepo.findByName(name)
    }
}