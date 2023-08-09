package com.example.petclinic.db.repo

import com.example.petclinic.db.entity.Owner
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.data.jpa.repository.Query
import org.springframework.data.repository.query.Param
import org.springframework.stereotype.Repository

/**
 * @author Grigoriy Zemlyanskiy
 * @version 1.0
 * interface OwnerRepo
 */
@Repository
interface OwnerRepo: JpaRepository<Owner, Long>{
    @Query(value
    = "select * "
            + "from owner  "
            + "where full_name  LIKE %:name% ",
        nativeQuery = true)
    fun findByName(@Param("name") name: String?): ArrayList<Owner>

    @Query(value
    = "select * "
            + "from owner  "
            + "where phone  LIKE %:phone% ",
        nativeQuery = true)
    fun findByPhone(@Param("phone") phone: String?): ArrayList<Owner>?


}