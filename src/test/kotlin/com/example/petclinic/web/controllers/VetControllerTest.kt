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
import org.springframework.test.web.servlet.result.MockMvcResultHandlers
import org.springframework.test.web.servlet.result.MockMvcResultMatchers



/**
 * @author Grigoriy Zemlyanskiy
 * @version 1.0
 * annotation class ErrorTransaction
 */
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@AutoConfigureMockMvc
class VetControllerTest @Autowired constructor(
    val vetController: VetController,
    val restTemplate: TestRestTemplate,
    val vetComponent: VetComponent,

){
    @Autowired
    lateinit var mockMvc: MockMvc
    @LocalServerPort
    private val port = 0
    private val localhost = "http://localhost:"
    private val localhostVet = localhost +port+"/vet"
    private val localhostVetByName = localhost +port+"/vet/name"
    private val localhostVetByPhone = localhost +port+"/vet/phone"
    private val vetUrl = "http://localhost:8080/vet"
    companion object{
        private val vetCreate = """{"fullName":"Test","address":"london","phone":"23-35-2324","birthday":"1990-01-30","gender":"MALE"}"""
        private val vetUpdate =  """ {"full_name":"Testupodate","address":"Testypdate","phone":"73-35-2324","birthday":"1990-01-30","gender":"MALE","qualification":"first"}"""
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
        mockMvc.get(localhost +port+"/vet/1").andExpect {
            content {
                string("""{"full_name":"Russ Abbot","address":"Baltimore","phone":"73-35-2324","gender":"MALE","qualification":"first","birthday":"1990-01-30"}""")
            }
        }
    }

    @Test
    fun findByName() {
        mockMvc.perform(
            MockMvcRequestBuilders
                .get(localhostVetByName)
                .content("Russ")
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON)
        ) .andDo(MockMvcResultHandlers.print())
            .andExpect(MockMvcResultMatchers.status().is3xxRedirection)
            .andExpect(MockMvcResultMatchers.content().contentType("application/json"))
            .andExpect(MockMvcResultMatchers.jsonPath("$[0].full_name").value("Russ Abbot"))
            .andExpect(MockMvcResultMatchers.jsonPath("$[0].address").value("Baltimore"))
            .andExpect(MockMvcResultMatchers.jsonPath("$[0].phone").value("73-35-2324"))
            .andExpect(MockMvcResultMatchers.jsonPath("$[0].birthday").value("1990-01-30"))
            .andExpect(MockMvcResultMatchers.jsonPath("$[0].gender").value("MALE"))
            .andExpect(MockMvcResultMatchers.jsonPath("$[0].qualification").value("first"))
    }

    @Test
    fun findByPhone() {
        mockMvc.perform(
            MockMvcRequestBuilders
                .get(localhostVetByPhone)
                .param("phone", "73-35-2324")
                .content("73-35-2324")
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON)
        ) .andDo(MockMvcResultHandlers.print())
            .andExpect(MockMvcResultMatchers.status().is3xxRedirection)
            .andExpect(MockMvcResultMatchers.content().contentType("application/json"))
            .andExpect(MockMvcResultMatchers.jsonPath("$.full_name").value("Russ Abbot"))
            .andExpect(MockMvcResultMatchers.jsonPath("$.address").value("Baltimore"))
            .andExpect(MockMvcResultMatchers.jsonPath("$.phone").value("73-35-2324"))
            .andExpect(MockMvcResultMatchers.jsonPath("$.birthday").value("1990-01-30"))
            .andExpect(MockMvcResultMatchers.jsonPath("$.gender").value("MALE"))
            .andExpect(MockMvcResultMatchers.jsonPath("$.qualification").value("first"))
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
                .put( vetUrl+"/$id")
                .content(vetUpdate)
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON)
        )
        val vetFromTableUpdate = vetComponent.findById(id)
        assertEquals(vetFromTableUpdate.id, id)
        assertEquals(vetFromTableUpdate.fullName, "Testupodate")
        assertEquals(vetFromTableUpdate.address, "Testypdate")
        assertEquals(vetFromTableUpdate.gender, Gender.MALE)
        assertEquals(vetFromTableUpdate.phone, "73-35-2324")
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