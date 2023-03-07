package com.example.petclinic.db.repo

import com.example.petclinic.db.entity.Owner
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.stereotype.Repository

@Repository
interface OwnerRepo: JpaRepository<Owner, Long>