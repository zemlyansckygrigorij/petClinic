package com.example.petclinic.db.repo

import com.example.petclinic.db.entity.Owner
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.data.jpa.repository.Query
import org.springframework.data.repository.query.Param
import org.springframework.stereotype.Repository

@Repository
interface OwnerRepo: JpaRepository<Owner, Long>{
    @Query(value
    = "select * "
            + "from owner  "
            + "where full_name  LIKE %:name% ",
        nativeQuery = true)
    open fun findByName(@Param("name") name: String?): ArrayList<Owner>

    @Query(value
    = "select * "
            + "from owner  "
            + "where phone  LIKE %:phone% ",
        nativeQuery = true)
    open fun findByPhone(@Param("phone") phone: String?): Owner?


}