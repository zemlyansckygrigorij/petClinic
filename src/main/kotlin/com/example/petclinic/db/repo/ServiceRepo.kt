package com.example.petclinic.db.repo

import com.example.petclinic.db.entity.Service
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.data.jpa.repository.Query
import org.springframework.data.repository.query.Param
import org.springframework.stereotype.Repository

/**
 * @author Grigoriy Zemlyanskiy
 * @version 1.0
 * interface ServiceRepo
 */
@Repository
interface ServiceRepo: JpaRepository<Service, Long>{
    @Query(value
    = "select * "
            + "from services  "
            + "where name  LIKE %:name% ",
        nativeQuery = true)
    open fun findByName(@Param("name") name: String?): ArrayList<Service>
}