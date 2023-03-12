package com.example.petclinic.db.services

import com.example.petclinic.db.repo.ServiceRepo
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Service

/**
 * @author Grigoriy Zemlyanskiy
 * @version 1.0
 * class Owner
 */
@Service
class ServiceComponentImpl @Autowired constructor(
    val serviceRepo: ServiceRepo
): ServiceComponent {
    override fun save(service: com.example.petclinic.db.entity.Service) {
        serviceRepo.save(service)
    }

    override fun findById(id: Long): com.example.petclinic.db.entity.Service {
        return serviceRepo.findById(id).orElseThrow { throw Exception() }
    }

    override fun findAll(): ArrayList<com.example.petclinic.db.entity.Service> {
        val serviceList = ArrayList<com.example.petclinic.db.entity.Service>()
        if(serviceList.addAll( serviceRepo.findAll().toList())){
            return serviceList
        }else{
            throw Exception()
        }
    }

    override fun deleteById(id: Long) {
        serviceRepo.deleteById(id)
    }

    override fun findByName(name: String): ArrayList<com.example.petclinic.db.entity.Service> {
        return serviceRepo.findByName(name)
    }
}