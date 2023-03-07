package com.example.petclinic.db.repo

import com.example.petclinic.db.entity.Service
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.stereotype.Repository

@Repository
interface ServiceRepo: JpaRepository<Service, Long>