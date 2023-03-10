package com.example.petclinic.db.services

import com.example.petclinic.db.entity.Pet
import com.example.petclinic.db.repo.PetRepo
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Service

@Service
class PetComponentImpl @Autowired constructor(
    val petRepo: PetRepo
): PetComponent{
    override fun save(pet: Pet) {
        petRepo.save(pet)
    }

    override fun findById(id: Long): Pet {
        return petRepo.findById(id).orElseThrow { throw Exception() }
    }

    override fun findAll(): ArrayList<Pet> {
        val petList = ArrayList<Pet>()
        if(petList.addAll( petRepo.findAll().toList())){
            return petList
        }else{
            throw Exception()
        }
    }

    override fun deleteById(id: Long) {
        return petRepo.deleteById(id)
    }

    override fun findByName(name: String): ArrayList<Pet> {
        return petRepo.findByName(name)
    }

    override fun findByOwnerId(id:Long): ArrayList<Pet> {
        return petRepo.findByOwner(id)
    }
}