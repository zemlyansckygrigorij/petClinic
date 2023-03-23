package com.example.petclinic.web.controllers

import com.example.petclinic.db.entity.Gender
import com.example.petclinic.db.services.OwnerComponent
import org.junit.jupiter.api.Assertions.assertFalse
import org.junit.jupiter.api.Assertions.assertTrue
import org.assertj.core.api.Assertions.assertThat
import org.hamcrest.Matchers
import org.junit.jupiter.api.Assertions.*
import org.junit.jupiter.api.Test
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.boot.test.web.client.TestRestTemplate
import org.springframework.boot.test.web.server.LocalServerPort
import org.springframework.http.MediaType
import org.springframework.test.web.servlet.MockMvc
import org.springframework.test.web.servlet.get
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders
import org.springframework.test.web.servlet.result.MockMvcResultMatchers

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@AutoConfigureMockMvc
class OwnerControllerTest @Autowired constructor(
    val ownerController: OwnerController,
    val restTemplate: TestRestTemplate,
    val ownerComponent: OwnerComponent
){
    @Autowired
    lateinit var mockMvc: MockMvc

    @LocalServerPort
    private val port = 0
    private val localhost = "http://localhost:"
    private val localhostOwner = localhost +port+"/owner"
    private val localhostOwnerId = localhost +port+"/owner/1"
    private val localhostOwnerByName = localhost +port+"/owner/name/Bradley"
    private val localhostOwnerByPhone = localhost +port+"/owner/phone/23-35-2324"
    private val ownerUrl = "http://localhost:8080/owner"
    companion object{
        private val ownerCreate = """{"fullName":"Test","address":"london","phone":"23-35-2324","birthday":"1990-01-30","gender":"MALE"}"""
        private val ownerUpdateEnd =""" ,"fullName":"TestUpdate","address":"london","phone":"23-35-2324","birthday":"1990-01-30","gender":"MALE"}"""
        private val ownerUpdateStart =""" {"id": """
    }

    @Test
    fun contextLoad(){
        assertThat(ownerController).isNotNull()
        assertThat(ownerComponent).isNotNull()
    }

    @Test
    fun findAll() {
        assertTrue(
            restTemplate
                .getForObject(localhost +port+"/owner",String::class.java)
                .toString()
                .contains("Bradley Alexander Abbe")
        )
        assertFalse(
            restTemplate
                .getForObject(localhost +port+"/owner",String::class.java)
                .toString()
                .contains("adsfasdfgsdfghfsdgyher")
        )
        mockMvc.perform(MockMvcRequestBuilders.get(localhostOwner))
            .andExpect(MockMvcResultMatchers.jsonPath("$", Matchers.hasSize<String>(17)))
    }

    @Test
    fun findById() {
        mockMvc.get(localhostOwnerId).andExpect {
            content {
                string("""{"id":1,"fullName":"Bradley Alexander Abbe","address":"Baltimore","phone":"23-35-2324","birthday":"1990-01-30","gender":"MALE"}""")
            }
        }
    }

    @Test
    fun findByName() {
        mockMvc.get(localhostOwnerByName).andExpect {
            content {
                string("""[{"id":1,"fullName":"Bradley Alexander Abbe","address":"Baltimore","phone":"23-35-2324","birthday":"1990-01-30","gender":"MALE"}]""")
            }
        }
    }

    @Test
    fun findByPhone() {
        mockMvc.get(localhostOwnerByPhone).andExpect {
            content {
                string("""{"id":1,"fullName":"Bradley Alexander Abbe","address":"Baltimore","phone":"23-35-2324","birthday":"1990-01-30","gender":"MALE"}""")
            }
        }
    }

    @Test
    fun commonTest(){
        val owners = ownerComponent.findAll()
        val id = owners.maxBy { it.id }.id +1
        mockMvc.perform(
            MockMvcRequestBuilders
                .post(ownerUrl)
                .content(ownerCreate)
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON)
        )
            .andExpect(MockMvcResultMatchers.status().isCreated())
            .andExpect(MockMvcResultMatchers.jsonPath("$.id").exists())
        assertEquals(ownerComponent.findAll().size, owners.size+1)

        val ownerFromTable = ownerComponent.findById(id)
        assertEquals(ownerFromTable.id, id)
        assertEquals(ownerFromTable.fullName, "Test")
        assertEquals(ownerFromTable.address, "london")
        assertEquals(ownerFromTable.gender, Gender.MALE)
        assertEquals(ownerFromTable.phone, "23-35-2324")
        assertEquals(ownerFromTable.birthday.toString(), "1990-01-30")

        mockMvc.perform(
            MockMvcRequestBuilders
                .put(ownerUrl+"/$id")
                .content(ownerUpdateStart +id+ownerUpdateEnd)
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON)
        )

        val ownerFromTableUpdate = ownerComponent.findById(id)
        assertEquals(ownerFromTableUpdate.id, id)
        assertEquals(ownerFromTableUpdate.fullName, "TestUpdate")
        assertEquals(ownerFromTableUpdate.address, "london")
        assertEquals(ownerFromTableUpdate.gender, Gender.MALE)
        assertEquals(ownerFromTableUpdate.phone, "23-35-2324")
        assertEquals(ownerFromTableUpdate.birthday.toString(), "1990-01-30")
        assertEquals(ownerComponent.findAll().size, owners.size+1)

        mockMvc.perform(
            MockMvcRequestBuilders
                .delete(ownerUrl+"/$id")
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON))
            .andExpect(MockMvcResultMatchers.status().isOk())
        assertEquals(ownerComponent.findAll().size, owners.size)
    }
}
