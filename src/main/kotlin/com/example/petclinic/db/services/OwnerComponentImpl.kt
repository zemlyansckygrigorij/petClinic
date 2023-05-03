package com.example.petclinic.db.services

import com.example.petclinic.db.entity.Owner
import com.example.petclinic.db.repo.OwnerRepo
import org.springframework.beans.factory.annotation.Autowired
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
    override fun save(owner: Owner): Owner {
        return ownerRepo.save(owner)
    }
    @Transactional(
        isolation = Isolation.READ_UNCOMMITTED,//уровень изоляции
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
       return ownerRepo.findById(id).orElseThrow { throw Exception() }
    }

    override fun findAll(): ArrayList<Owner> {
        val ownerList = ArrayList<Owner>()
        if(ownerList.addAll( ownerRepo.findAll().toList())){
            return ownerList
        }else{
            throw Exception()
        }
    }

    override fun deleteById(id: Long) {
        ownerRepo.deleteById(id)
    }

    override fun findByName(name: String): ArrayList<Owner> {
        return ownerRepo.findByName(name)
    }

    override fun findByPhone(phone: String): Owner {
        ownerRepo.findByPhone(phone).let{
           return  it!!
        }
        throw Exception()
    }
}
/**
 * https://habr.com/ru/companies/otus/articles/649093/
 * https://sysout.ru/transaction-propagation/
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
 */