package com.example.petclinic.db.services

import com.example.petclinic.db.entity.Services
import com.example.petclinic.db.repo.ServiceRepo
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Service

/**
 * @author Grigoriy Zemlyanskiy
 * @version 1.0
 * class ServiceComponentImpl
 */
@Service
class ServiceComponentImpl @Autowired constructor(
    val serviceRepo: ServiceRepo
): ServiceComponent {
    override fun save(service: Services): Services {
        return serviceRepo.save(service)
    }

    override fun findById(id: Long): Services {
        return serviceRepo.findById(id).orElseThrow { throw Exception() }
    }

    override fun findAll(): ArrayList<Services> {
        val serviceList = ArrayList<Services>()
        if(serviceList.addAll( serviceRepo.findAll().toList())){
            return serviceList
        }else{
            throw Exception()
        }
    }

    override fun deleteById(id: Long) {
        serviceRepo.deleteById(id)
    }

    override fun findByName(name: String): ArrayList<Services> {
        return serviceRepo.findByName(name)
    }
}