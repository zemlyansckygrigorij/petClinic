package com.example.petclinic.db.services

import com.example.petclinic.db.entity.Vet
import com.example.petclinic.db.repo.VetRepo
import com.example.petclinic.logging.TransactionLogging
import com.example.petclinic.web.exceptions.ListOfVetsNotFoundException
import com.example.petclinic.web.exceptions.VetNotFoundException
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
 * class VetComponentImpl
 */
@Service
class VetComponentImpl @Autowired constructor(
    val vetRepo: VetRepo
): VetComponent {


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
    override fun save(vet: Vet): Vet {
        return vetRepo.save(vet)
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
    override fun findById(id: Long): Vet {
       return vetRepo.findById(id).orElseThrow { throw VetNotFoundException() }
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
    override fun findAll(): ArrayList<Vet> {
        val vetList = ArrayList<Vet>()
        if(vetList.addAll( vetRepo.findAll().toList())){
            return vetList
        }else{
            throw ListOfVetsNotFoundException()
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
        vetRepo.findById(id).orElseThrow { throw VetNotFoundException() }
        vetRepo.deleteById(id)
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
    override fun findByName(name: String): ArrayList<Vet> {
        return vetRepo.findByName(name)
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
    override fun findByPhone(phone: String): ArrayList<Vet> {
        vetRepo.findByPhone(phone).let{
            return  it!!
        }
    }
}