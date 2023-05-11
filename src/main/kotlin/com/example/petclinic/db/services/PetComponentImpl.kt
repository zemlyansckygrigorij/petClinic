package com.example.petclinic.db.services

import com.example.petclinic.db.entity.Pet
import com.example.petclinic.db.repo.PetRepo
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
 * class PetComponentImpl
 */
@Service
class PetComponentImpl @Autowired constructor(
    val petRepo: PetRepo
): PetComponent{

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
    override fun save(pet: Pet): Pet{
        return petRepo.save(pet)
    }

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
    override fun findById(id: Long): Pet {
        return petRepo.findById(id).orElseThrow { throw Exception() }
    }

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
    override fun findAll(): ArrayList<Pet> {
        val petList = ArrayList<Pet>()
        if(petList.addAll( petRepo.findAll().toList())){
            return petList
        }else{
            throw Exception()
        }
    }

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
        return petRepo.deleteById(id)
    }

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
    override fun findByName(name: String): ArrayList<Pet> {
        return petRepo.findByName(name)
    }

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
    override fun findByOwnerId(id:Long): ArrayList<Pet> {
        return petRepo.findByOwner(id)
    }
}