package com.example.petclinic.db.repo

import com.example.petclinic.db.entity.Pet
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.stereotype.Repository

@Repository
interface PetRepo: JpaRepository<Pet, Long>