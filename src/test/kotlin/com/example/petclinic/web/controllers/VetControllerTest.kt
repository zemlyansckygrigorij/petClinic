package com.example.petclinic.web.controllers

import com.example.petclinic.db.entity.Gender
import com.example.petclinic.db.services.VetComponent
import org.assertj.core.api.Assertions
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
class VetControllerTest @Autowired constructor(
    val vetController: VetController,
    val restTemplate: TestRestTemplate,
    val vetComponent: VetComponent,
    val mockMvc: MockMvc
){
    @LocalServerPort
    private val port = 0
    private val localhost = "http://localhost:"
    private val localhostVet = localhost +port+"/vet"
    private val localhostVetId = localhost +port+"/vet/22"
    private val localhostVetByName = localhost +port+"/vet/name/Russ"
    private val localhostVetByPhone = localhost +port+"/vet/phone/73-35-2324"
    private val vetUrl = "http://localhost:8080/vet"
    companion object{
        private val vetCreate = """{"fullName":"Test","address":"london","phone":"23-35-2324","birthday":"1990-01-30","gender":"MALE"}"""
        private val vetUpdateEnd =""" ,"fullName":"TestUpdate","address":"london","phone":"23-35-2324","birthday":"1990-01-30","gender":"MALE"}"""
        private val vetUpdateStart =""" {"id": """
    }
    @Test
    fun contextLoad(){
        Assertions.assertThat(vetController).isNotNull()
        Assertions.assertThat(vetComponent).isNotNull()
    }
    @Test
    fun findAll() {
        assertTrue(
            restTemplate
                .getForObject(localhost +port+"/vet",String::class.java)
                .toString()
                .contains("Russ Abbot")
        )
        assertFalse(
            restTemplate
                .getForObject(localhost +port+"/vet",String::class.java)
                .toString()
                .contains("adsfasdfgsdfghfsdgyher")
        )
        mockMvc.perform(MockMvcRequestBuilders.get(localhostVet))
            .andExpect(MockMvcResultMatchers.jsonPath("$", Matchers.hasSize<String>(17)))

    }

    @Test
    fun findById() {
        mockMvc.get(localhostVetId).andExpect {
            content {
                string("""{"id":22,"fullName":"Russ Abbot","address":"Baltimore","phone":"73-35-2324","birthday":"1990-01-30","gender":"MALE","qualification":"first"}""")
            }
        }
    }

    @Test
    fun findByName() {
        mockMvc.get(localhostVetByName).andExpect {
            content {
                string("""[{"id":22,"fullName":"Russ Abbot","address":"Baltimore","phone":"73-35-2324","birthday":"1990-01-30","gender":"MALE","qualification":"first"}]""")
            }
        }
    }

    @Test
    fun findByPhone() {
        mockMvc.get(localhostVetByPhone).andExpect {
            content {
                string("""{"id":22,"fullName":"Russ Abbot","address":"Baltimore","phone":"73-35-2324","birthday":"1990-01-30","gender":"MALE","qualification":"first"}""")
            }
        }
    }
    @Test
    fun commonTest(){
        val numberVet = vetComponent.findAll().size
        mockMvc.perform(
            MockMvcRequestBuilders
                .post(vetUrl)
                .content(vetCreate)
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON)
        )
            .andExpect(MockMvcResultMatchers.status().isCreated())
            .andExpect(MockMvcResultMatchers.jsonPath("$.id").exists())
        assertEquals(vetComponent.findAll().size, numberVet+1)

        val id = vetComponent.findAll().maxBy { it.id }.id
        val vetFromTable = vetComponent.findById(id)
        assertEquals(vetFromTable.id, id)
        assertEquals(vetFromTable.fullName, "Test")
        assertEquals(vetFromTable.address, "london")
        assertEquals(vetFromTable.gender, Gender.MALE)
        assertEquals(vetFromTable.phone, "23-35-2324")
        assertEquals(vetFromTable.birthday.toString(), "1990-01-30")

        mockMvc.perform(
            MockMvcRequestBuilders
                .put(vetUrl+"/$id")
                .content(vetUpdateStart +id+ vetUpdateEnd)
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON)
        )

        val vetFromTableUpdate = vetComponent.findById(id)
        assertEquals(vetFromTableUpdate.id, id)
        assertEquals(vetFromTableUpdate.fullName, "TestUpdate")
        assertEquals(vetFromTableUpdate.address, "london")
        assertEquals(vetFromTableUpdate.gender, Gender.MALE)
        assertEquals(vetFromTableUpdate.phone, "23-35-2324")
        assertEquals(vetFromTableUpdate.birthday.toString(), "1990-01-30")
        assertEquals(vetComponent.findAll().size, numberVet+1)

        mockMvc.perform(
            MockMvcRequestBuilders
                .delete(vetUrl+"/$id")
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON))
            .andExpect(MockMvcResultMatchers.status().isOk())
        assertEquals(vetComponent.findAll().size, numberVet)
    }
}