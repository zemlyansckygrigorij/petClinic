package com.example.petclinic.db.repo

import com.example.petclinic.db.entity.Vet
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.stereotype.Repository

@Repository
interface VetRepo: JpaRepository<Vet, Long>