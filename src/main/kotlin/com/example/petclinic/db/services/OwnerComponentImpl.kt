package com.example.petclinic.db.services

import com.example.petclinic.db.entity.Owner
import com.example.petclinic.db.repo.OwnerRepo
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Service

@Service
class OwnerComponentImpl @Autowired constructor(
    val  ownerRepo: OwnerRepo
): OwnerComponent{
    override fun save(owner: Owner) {
        ownerRepo.save(owner)
    }

    override fun findById(id: Long): Owner {
       return ownerRepo.findById(id).orElseThrow { throw Exception() }
    }

    override fun findAll(): ArrayList<Owner> {
        val ownerList = ArrayList<Owner>()
        if(ownerList.addAll( ownerRepo.findAll().toList())){
            return ownerList
        }else{
            throw Exception()
        }
    }

    override fun deleteById(id: Long) {
        ownerRepo.deleteById(id)
    }

    override fun findByName(name: String): ArrayList<Owner> {
        return ownerRepo.findByName(name)
    }

    override fun findByPhone(phone: String): Owner {
        ownerRepo.findByPhone(phone).let{
           return  it!!
        }
        throw Exception()
    }
}