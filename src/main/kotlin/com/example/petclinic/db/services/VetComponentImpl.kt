package com.example.petclinic.db.services

import com.example.petclinic.db.entity.Vet
import com.example.petclinic.db.repo.VetRepo
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Service

/**
 * @author Grigoriy Zemlyanskiy
 * @version 1.0
 * class VetComponentImpl
 */
@Service
class VetComponentImpl @Autowired constructor(
    val vetRepo: VetRepo
): VetComponent {
    override fun save(vet: Vet) {
        vetRepo.save(vet)
    }

    override fun findById(id: Long): Vet {
       return vetRepo.findById(id).orElseThrow { throw Exception() }
    }

    override fun findAll(): ArrayList<Vet> {
        val vetList = ArrayList<Vet>()
        if(vetList.addAll( vetRepo.findAll().toList())){
            return vetList
        }else{
            throw Exception()
        }
    }

    override fun deleteById(id: Long) {
        vetRepo.deleteById(id)
    }

    override fun findByName(name: String): ArrayList<Vet> {
        return vetRepo.findByName(name)
    }

    override fun findByPhone(phone: String): Vet {
        vetRepo.findByPhone(phone).let{
            return  it!!
        }
        throw Exception()
    }
}