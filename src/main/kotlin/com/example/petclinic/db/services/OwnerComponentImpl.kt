package com.example.petclinic.db.services

import com.example.petclinic.db.entity.Owner
import com.example.petclinic.db.repo.OwnerRepo
import com.example.petclinic.logging.TransactionLogging
import com.example.petclinic.web.exceptions.ListOfOwnersNotFoundException
import com.example.petclinic.web.exceptions.OwnerNotFoundException
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
 * class OwnerComponentImpl
 */
@Service
class OwnerComponentImpl @Autowired constructor(
    val  ownerRepo: OwnerRepo
): OwnerComponent{
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
    override fun save(owner: Owner): Owner {
        println( " owner.address-"+owner.address)
        println( " owner.fullName-"+owner.fullName)
        println( " owner.id-"+owner.id)
        return ownerRepo.save(owner)
    }

    @TransactionLogging
    @Lock(LockModeType.OPTIMISTIC)
    @Transactional(
        isolation = Isolation.READ_COMMITTED,//уровень изоляции
        label = ["label"],//метка для диспетчера транзакций
        readOnly = true,//невозможно применить CREATE, UPDATE, DELETE.

        timeout = 10,//Для создаваемой транзакции может быть указан таймаут в секундах,
        // при превышении которого транзакция будет прервана и откачена.
        // Таймаут транзакции ограничивает максимальную длительность запросов к базе данных.

        propagation = Propagation.SUPPORTS,//propagation — определяет,
        // как границы транзакции распространяются на другие методы,
        // которые будут вызваны прямо или косвенно из аннотированного блока.
        // По умолчанию propagation задается как REQUIRED и значит,
        // что она запускается, если еще нет ни одной транзакции.
        // В противном случае текущая транзакция будет использована выполняющимся на данный момент методом.

        rollbackFor =(arrayOf(RuntimeException::class,IOException::class)),
        noRollbackFor = (arrayOf(SQLException::class)),

        //value и transactionManager — эти атрибуты могут быть использованы для предоставления ссылки на TransactionManager,
        // которая будет использоваться при обработке транзакции для аннотированного блока
        transactionManager ="transactionManager",
        value = ""
    )
    override fun findById(id: Long): Owner {
       return ownerRepo.findById(id).orElseThrow { throw OwnerNotFoundException() }
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
    override fun findAll(): ArrayList<Owner> {
        val ownerList = ArrayList<Owner>()
        if(ownerList.addAll( ownerRepo.findAll().toList())){
            return ownerList
        }else{
            throw ListOfOwnersNotFoundException()
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
        ownerRepo.findById(id).orElseThrow { throw OwnerNotFoundException() }
        ownerRepo.deleteById(id)
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
    override fun findByName(name: String): ArrayList<Owner> {
        return ownerRepo.findByName(name)
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
    override fun findByPhone(phone: String): ArrayList<Owner> {
        ownerRepo.findByPhone(phone).let{
           return  it!!
        }
        throw Exception()
    }
}
/**
 * https://habr.com/ru/companies/otus/articles/649093/
 * https://sysout.ru/transaction-propagation/
 * https://sysout.ru/transactional-urovni-izolyatsii/#more-2728
 * propagation = Propagation.SUPPORTS
 * propagation=REQUIRED задает следующее поведение:
 *
 * 1) Если метод  вызывается вне транзакции, для него создается отдельная транзакция.
 * 2)Если же метод вызывается из метода сервиса, в котором уже есть  транзакция (наш случай),
 * то updatePublished()  вызывается в рамках этой транзакции.
 *
 *
 * propagation=REQUIRED_NEW
 * В случае REQUIRED_NEW для внутреннего метода создается своя отдельная транзакция.
 * Пока выполняется внутренний метод, внешняя транзакция приостанавливается.
 * Но это две отдельных транзакции,
 * и исключение во внешнем методе не повлияет на успешное подтверждение внутренней транзакции.
 *
 *
 *
 *
 *
 * propagation=SUPPORTS
 * SUPPORTS использует транзакцию во внешнем методе, если она есть.
 * Но если нет, своя транзакция для внутреннего метода создаваться не будет.
 * А без транзакции все команды внутреннего метода будут выполнены в режиме автофиксации (AUTOCOMMIT).
 *
 * В режиме AUTOCOMMIT каждая команда автоматически подтверждается
 * (как бы обрамляется своей отдельной транзакцией — commit-ом, происходит это на уровне базы данных).
 * То есть если б в методе updatePublished() было несколько SQL-операторов update,
 * то в режиме AUTOCOMMIT часть из них могла бы выполниться, а часть — нет.
 * Попробуем поставить эту настройку.
 *
 *
 * Propagation.NOT_SUPPORTED
 * В отличие от SUPPORTS, здесь команды ускользают от транзакции,
 * даже если вызываются в рамках ее.
 * То есть если внешний метод аннотирован @Transactional:
 *
 *
 *
 * Propagation.NEVER
 * Propagation.NEVER не терпит транзакции снаружи и выбрасывает исключение, если транзакция обнаружена. Проверим это.
 *
 *
 * Propagation.MANDATORY
 * Наконец, Propagation.MANDATORY требует внешнюю транзакцию, а иначе выбрасывается исключение.
 *
 * REQUIRED to tell Spring to either join an active transaction or to start a new one
 * if the method gets called without a transaction. This is the default behavior.
 *
 * SUPPORTS to join an activate transaction if one exists. If the method gets called without an active transaction,
 * this method will be executed without a transactional context.
 * MANDATORY to join an activate transaction if one exists or to throw an Exception
 * if the method gets called without an active transaction.
 * NEVER to throw an Exception if the method gets called in the context of an active transaction.
 * NOT_SUPPORTED to suspend an active transaction and to execute the method without any transactional context.
 * REQUIRES_NEW to always start a new transaction for this method.
 * If the method gets called with an active transaction, that transaction gets suspended until this method got executed.
 * NESTED to start a new transaction if the method gets called without an active transaction.
 * If it gets called with an active transaction, Spring sets a savepoint and rolls back to that savepoint if an Exception occurs.
 */