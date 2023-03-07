package com.example.petclinic.db.repo

import com.example.petclinic.db.entity.Owner
import com.example.petclinic.db.entity.Vet
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.data.jpa.repository.Query
import org.springframework.data.repository.query.Param
import org.springframework.stereotype.Repository

@Repository
interface VetRepo: JpaRepository<Vet, Long>{
    @Query(value
    = "select * "
            + "from vet  "
            + "where full_name  LIKE %:name% ",
        nativeQuery = true)
    open fun findByName(@Param("name") name: String?): ArrayList<Vet>

    @Query(value
    = "select * "
            + "from vet  "
            + "where phone  LIKE %:phone% ",
        nativeQuery = true)
    open fun findByPhone(@Param("phone") phone: String?): Vet?


}