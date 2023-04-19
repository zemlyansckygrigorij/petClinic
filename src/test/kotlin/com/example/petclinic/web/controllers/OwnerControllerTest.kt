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
import org.springframework.test.web.servlet.result.MockMvcResultHandlers
import org.springframework.test.web.servlet.result.MockMvcResultMatchers
import org.springframework.test.web.servlet.result.MockMvcResultMatchers.*


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
    private val localhostOwnerByName = localhost +port+"/owner/name"
    private val localhostOwnerByPhone = localhost +port+"/owner/phone"
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
        mockMvc.perform(
            MockMvcRequestBuilders
                .get(localhostOwnerByName)
                .param("name", "Bradley")
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON)
        ) .andDo(MockMvcResultHandlers.print())
            .andExpect(status().is3xxRedirection)
            .andExpect(content().contentType("application/json"))
            .andExpect(jsonPath("$[0].id").value("1"))
            .andExpect(jsonPath("$[0].fullName").value("Bradley Alexander Abbe"))
            .andExpect(jsonPath("$[0].address").value("Baltimore"))
            .andExpect(jsonPath("$[0].phone").value("23-35-2324"))
            .andExpect(jsonPath("$[0].birthday").value("1990-01-30"))
            .andExpect(jsonPath("$[0].gender").value("MALE"))
            .andExpect(jsonPath("$[0].id").exists())
    }

    @Test
    fun findByPhone() {
        mockMvc.perform(
            MockMvcRequestBuilders
                .get(localhostOwnerByPhone)
                .param("phone", "23-35-2324")
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON)
        ) .andDo(MockMvcResultHandlers.print())
            .andExpect(status().is3xxRedirection)
            .andExpect(content().contentType("application/json"))
            .andExpect(jsonPath("$.id").value("1"))
            .andExpect(jsonPath("$.fullName").value("Bradley Alexander Abbe"))
            .andExpect(jsonPath("$.address").value("Baltimore"))
            .andExpect(jsonPath("$.phone").value("23-35-2324"))
            .andExpect(jsonPath("$.birthday").value("1990-01-30"))
            .andExpect(jsonPath("$.gender").value("MALE"))
            .andExpect(jsonPath("$.id").exists())
    }

    @Test
    fun commonTest(){
        val numberOwners = ownerComponent.findAll().size

        mockMvc.perform(
            MockMvcRequestBuilders
                .post(ownerUrl)
                .content(ownerCreate)
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON)
        )
            .andExpect(MockMvcResultMatchers.status().isCreated())
            .andExpect(MockMvcResultMatchers.jsonPath("$.id").exists())

        assertEquals(ownerComponent.findAll().size, numberOwners+1)

        val id = ownerComponent.findAll().maxBy { it.id }.id
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
        assertEquals(ownerComponent.findAll().size, numberOwners+1)

        mockMvc.perform(
            MockMvcRequestBuilders
                .delete(ownerUrl+"/$id")
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON))
            .andExpect(MockMvcResultMatchers.status().isOk())
        assertEquals(ownerComponent.findAll().size, numberOwners)
    }
}
