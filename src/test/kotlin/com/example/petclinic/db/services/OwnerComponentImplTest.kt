package com.example.petclinic.db.services

import com.example.petclinic.db.entity.Gender
import com.example.petclinic.db.entity.Owner
import org.junit.BeforeClass
import org.junit.jupiter.api.Assertions.*
import org.junit.jupiter.api.Test
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.test.context.jdbc.Sql
import java.text.SimpleDateFormat
import java.util.*


@SpringBootTest
class OwnerComponentImplTest @Autowired constructor(
 val  ownerComponent: OwnerComponent
){

  @BeforeClass
  @Sql("/db/sql/insertOwner.sql")
   fun before(){
     println(111)
   }
    @Sql("/db/sql/insertOwner.sql")
    @Test
    fun save() {
        var owners =ownerComponent.findAll()
        var id = owners.maxBy { it.id }.id+1
        assertEquals(owners.size, 17);
        var date = Date()
        var owner = Owner(id,"owner","Address","phone",date, Gender.MALE)
        var ownerSave = ownerComponent.save(owner)
        var ownerFromTable = ownerComponent.findById(ownerSave.id)
        assertEquals(ownerComponent.findAll().size, 18);
        assertEquals(ownerFromTable.gender, Gender.MALE )
        assertEquals(ownerFromTable.fullName,"owner")
        assertEquals(ownerFromTable.address,"Address")
        assertEquals(ownerFromTable.phone,"phone")

       val pattern = "yyyy-MM-dd"
       val simpleDateFormat = SimpleDateFormat(pattern)
       assertEquals(ownerFromTable.birthday.toString(),simpleDateFormat.format(date).toString())
    }

    @Test
    fun findById() {
    }

    @Test
    fun findAll() {
    }

    @Test
    @Sql("/db/sql/insertOwner.sql")
    fun deleteById() {
        var owners =ownerComponent.findAll()
        var id = owners.maxBy { it.id }.id+1
        assertEquals(owners.size, 17);
        var date = Date()
        var owner = Owner(id,"owner","Address","phone",date, Gender.MALE)
        var ownerSave = ownerComponent.save(owner)
        assertEquals(ownerComponent.findAll().size, 18);
        ownerComponent.deleteById(id)
        assertEquals(ownerComponent.findAll().size, 17);
    }

    @Test
    @Sql("/db/sql/insertOwner.sql")
    fun findByName() {
        var ownersFromTable = ownerComponent.findByName("Bradley")
        assertEquals(ownersFromTable.size, 1);
    }

    @Test
    @Sql("/db/sql/insertOwner.sql")
    fun findByPhone() {
     var ownerFromTable = ownerComponent.findByPhone("23-35-2324")
     assertEquals(ownerFromTable.gender, Gender.MALE )
     assertEquals(ownerFromTable.fullName,"Bradley Alexander Abbe")
     assertEquals(ownerFromTable.address,"london")
     assertEquals(ownerFromTable.gender,Gender.MALE)

    }

    @Test
    fun getOwnerRepo() {
     }
}