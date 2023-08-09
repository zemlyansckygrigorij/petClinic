package com.example.petclinic.web.controllers

import com.example.petclinic.db.entity.Gender
import com.example.petclinic.db.entity.Owner
import com.example.petclinic.db.services.OwnerComponent
import com.example.petclinic.web.model.request.OwnerRequest
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
import org.springframework.transaction.annotation.Transactional
import java.text.SimpleDateFormat
import java.util.*


@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@AutoConfigureMockMvc
@Transactional
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
        private val ownerCreate = """{"full_name":"Test","address":"london","phone":"23-35-2324","birthday":"1990-01-30","gender":"MALE"}"""
        private val ownerUpdateEnd =""" ,"full_name":"TestUpdate","address":"london","phone":"23-35-2324","birthday":"1990-01-30","gender":"MALE"}"""
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
                string("""{"full_name":"Bradley Alexander Abbe","address":"Baltimore","phone":"23-35-2324","gender":"MALE","birthday":"1990-01-30"}""")
            }
        }
    }

    @Test
    fun findByName() {
        mockMvc.perform(
            MockMvcRequestBuilders
                .get(localhostOwnerByName)
                .content("Bradley")
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON)
        ) .andDo(MockMvcResultHandlers.print())
            .andExpect(content().contentType("application/json"))
            .andExpect(jsonPath("$[0].full_name").value("Bradley Alexander Abbe"))
            .andExpect(jsonPath("$[0].address").value("Baltimore"))
            .andExpect(jsonPath("$[0].phone").value("23-35-2324"))
            .andExpect(jsonPath("$[0].birthday").value("1990-01-30"))
    }

    @Test
    fun findByPhone() {
        mockMvc.perform(
            MockMvcRequestBuilders
                .get(localhostOwnerByPhone)
                .content("23-35-2324")
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON)
        ) .andDo(MockMvcResultHandlers.print())
            .andExpect(status().is3xxRedirection)
            .andExpect(content().contentType("application/json"))
           // .andExpect(jsonPath("$.id").value("1"))
            .andExpect(jsonPath("$[0].full_name").value("Bradley Alexander Abbe"))
            .andExpect(jsonPath("$[0].address").value("Baltimore"))
            .andExpect(jsonPath("$[0].phone").value("23-35-2324"))
            .andExpect(jsonPath("$[0].birthday").value("1990-01-30"))
            .andExpect(jsonPath("$[0].gender").value("MALE"))
            //.andExpect(jsonPath("$.id").exists())
    }

    @Test
    fun commonTest(){
        val numberOwners = ownerComponent.findAll().size
        var date = Date()
        var owner = Owner(fullName = "owner", address = "Address", phone = "23-35-2324", birthday = date,gender = Gender.MALE)
        mockMvc.perform(
            MockMvcRequestBuilders
                .post("http://localhost:8080/owner")
                .content(OwnerRequest.getOwnerRequest(owner).toString())
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON)
        )
            .andExpect(MockMvcResultMatchers.status().isCreated())
            .andExpect(MockMvcResultMatchers.jsonPath("$.id").exists())

        assertEquals(ownerComponent.findAll().size, numberOwners+1)
        val owners =ownerComponent.findAll()
        val idOwner = owners.maxBy { it.id!! }.id!!


        mockMvc.perform(
            MockMvcRequestBuilders
                .get(localhostOwner+"/"+idOwner)
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON)
        ) .andDo(MockMvcResultHandlers.print())
            .andExpect(content().contentType("application/json"))
            .andExpect(jsonPath("$.full_name").value("owner"))
            .andExpect(jsonPath("$.address").value("Address"))
            .andExpect(jsonPath("$.phone").value("23-35-2324"))
            .andExpect(jsonPath("$.gender").value("MALE"))
            .andExpect(jsonPath("$.birthday").value(SimpleDateFormat("yyyy-MM-dd").format(date)))

        mockMvc.perform(
            MockMvcRequestBuilders
                .put(ownerUrl+"/$idOwner")
                .content(ownerUpdateStart +idOwner+ownerUpdateEnd)
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON)
        )

        mockMvc.get(localhostOwner+"/"+idOwner).andExpect {
            content {
                string("""{"full_name":"TestUpdate","address":"london","phone":"23-35-2324","gender":"MALE","birthday":"1990-01-30"}""")
            }
        }
        mockMvc.perform(
            MockMvcRequestBuilders
                .delete(ownerUrl+"/$idOwner")
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON))
            .andExpect(MockMvcResultMatchers.status().isOk())
        assertEquals(ownerComponent.findAll().size, numberOwners)
    }
}
