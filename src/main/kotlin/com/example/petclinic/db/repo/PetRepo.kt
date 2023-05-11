package com.example.petclinic.db.repo

import com.example.petclinic.db.entity.Owner
import com.example.petclinic.db.entity.Pet
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.data.jpa.repository.Query
import org.springframework.data.repository.query.Param
import org.springframework.stereotype.Repository

/**
 * @author Grigoriy Zemlyanskiy
 * @version 1.0
 * interface PetRepo
 */
@Repository
interface PetRepo: JpaRepository<Pet, Long>{
    @Query(value
    = "select * "
            + "from pet  "
            + "where name  LIKE %:name% ",
        nativeQuery = true)
    fun findByName(@Param("name") name: String?): ArrayList<Pet>

    @Query(value
    = "select * "
            + "from pet  "
            + "where id_owner = :id",
        nativeQuery = true)
    fun findByOwner(@Param("id") id: Long): ArrayList<Pet>


}