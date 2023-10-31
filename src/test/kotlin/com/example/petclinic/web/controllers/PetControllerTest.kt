package com.example.petclinic.web.controllers

import com.example.petclinic.db.entity.Gender
import com.example.petclinic.db.services.PetComponent
import org.junit.jupiter.api.Assertions.assertFalse
import org.junit.jupiter.api.Assertions.assertTrue
import org.assertj.core.api.Assertions.assertThat
import org.hamcrest.Matchers.*
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Test
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.boot.test.web.client.TestRestTemplate
import org.springframework.boot.test.web.server.LocalServerPort
import org.springframework.http.MediaType
import org.springframework.test.web.servlet.*
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get
import org.springframework.test.web.servlet.result.MockMvcResultHandlers
import org.springframework.test.web.servlet.result.MockMvcResultMatchers
import org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath
import org.springframework.test.web.servlet.result.MockMvcResultMatchers.status

/**
 * @author Grigoriy Zemlyanskiy
 * @version 1.0
 * annotation class ErrorTransaction
 */
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@AutoConfigureMockMvc
class PetControllerTest @Autowired constructor(
    val petController: PetController,
    val restTemplate: TestRestTemplate,
    val petComponent: PetComponent
){
    @LocalServerPort
    private  val port = 0
    private val localhostPet = localhost+port+"/pet"
    private val localhostPetId = localhost+port+"/pet/1"
    private val localhostPetName = localhost+port+"/pet/name"
    private val localhostPetByOwnerId = localhost+port+"/pet/owner/1"

    companion object{
        private const val petCreate = """{"kind":"CAT","name":"test123","age":1,"idOwner":1,"gender":"MALE"}"""
        private const val petUpdateEnd = ""","kind":"CAT","name":"testUpdate","age":1,"idOwner":1,"gender":"MALE"}"""
        private const val petUpdateStart = """{"id": """
        private const val petUrl = "http://localhost:8080/pet"
        private const val localhost = "http://localhost:"
    }

    @Autowired
    lateinit var mockMvc: MockMvc
    @Test
    fun contextLoad(){
        assertThat(petController).isNotNull()
        assertThat(restTemplate).isNotNull()
    }

    @Test
    fun findAll(){
        assertTrue(
            this
                .restTemplate
                .getForObject(localhost+port+"/pet", String::class.java)
            .toString()
            .contains("Oliver"))

        assertFalse(
            this
                .restTemplate
                .getForObject(localhost+port+"/pet", String::class.java)
                .toString()
                .contains("asdfasdfsad"))

        mockMvc.perform(get(localhostPet))
            .andExpect(jsonPath("$", hasSize<String>(21)))
    }

    @Test
    fun findById(){
        assertTrue(
            this
                .restTemplate
                .getForObject(localhostPetId, String::class.java)
                .toString()
                .contains("Oliver"))

        assertFalse(
            this
                .restTemplate
                .getForObject(localhostPetId, String::class.java)
                .toString()
                .contains("asdfasdfsad"))


        mockMvc.get(localhostPetId).andExpect {
            content {
                string("""{"id":1,"kind":"CAT","name":"Oliver","age":1,"idOwner":1,"gender":"MALE"}""")
            }
        }
    }
    @Test
    fun findByName(){
        mockMvc.perform(
            MockMvcRequestBuilders
                .get(localhostPetName)
                .param("name", "Oliver")
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON)
        ) .andDo(MockMvcResultHandlers.print())
            .andExpect(status().is3xxRedirection)
            .andExpect(MockMvcResultMatchers.content().contentType("application/json"))
            .andExpect(jsonPath("$[0].id").value("1"))
            .andExpect(jsonPath("$[0].name").value("Oliver"))
            .andExpect(jsonPath("$[0].kind").value("CAT"))
            .andExpect(jsonPath("$[0].age").value("1"))
            .andExpect(jsonPath("$[0].idOwner").value("1"))
            .andExpect(jsonPath("$[0].gender").value("MALE"))
            .andExpect(jsonPath("$[0].id").exists())
    }

    @Test
    fun findByOwnerId(){
        assertTrue(
            this
                .restTemplate
                .getForObject(localhostPetByOwnerId, String::class.java)
                .toString()
                .contains("Oliver"))
        assertFalse(
            this
                .restTemplate
                .getForObject(localhostPetByOwnerId, String::class.java)
                .toString()
                .contains("asdfasdfsad"))

        mockMvc.get(localhostPetByOwnerId).andExpect {
            content {
                string("""[{"id":1,"kind":"CAT","name":"Oliver","age":1,"idOwner":1,"gender":"MALE"},{"id":18,"kind":"DOG","name":"Daisy","age":9,"idOwner":1,"gender":"FEMALE"}]""".trimIndent())
            }
        }
    }

    @Test
    fun create() {
        val numberPets = petComponent.findAll().size

        mockMvc.perform(
            MockMvcRequestBuilders
                .post(petUrl)
                .content(petCreate)
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON)
        )
           .andExpect(status().isCreated())
           .andExpect(jsonPath("$.id").exists())
        assertEquals(petComponent.findAll().size, numberPets + 1)

        val id = petComponent.findAll().maxBy { it.id }.id
        val petFromTable = petComponent.findById(id)
        assertEquals(petFromTable.id, id)
        assertEquals(petFromTable.name, "test123")
        assertEquals(petFromTable.age, 1)
        assertEquals(petFromTable.gender, Gender.MALE)
        assertEquals(petFromTable.kind, "CAT")
        assertEquals(petFromTable.idOwner, 1)


        mockMvc.perform(
            MockMvcRequestBuilders
                .put(petUrl+"/$id")
                .content(petUpdateStart+id+petUpdateEnd)
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON)
        )
        val petFromTable1 = petComponent.findById(id)
        assertEquals(petFromTable1.id, id)
        assertEquals(petFromTable1.name, "testUpdate")
        assertEquals(petFromTable1.age, 1)
        assertEquals(petFromTable1.gender, Gender.MALE)
        assertEquals(petFromTable1.kind, "CAT")
        assertEquals(petFromTable1.idOwner, 1)
        assertEquals(petComponent.findAll().size, numberPets + 1)

        mockMvc.perform(
            MockMvcRequestBuilders
                .delete(petUrl+"/$id")
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())

        assertEquals(petComponent.findAll().size, numberPets)
    }
}



















