package com.example.petclinic.web.controllers

import com.example.petclinic.db.entity.Gender
import com.example.petclinic.db.services.ServiceComponent
import org.junit.jupiter.api.Assertions.*
import org.junit.jupiter.api.Test
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.boot.test.web.client.TestRestTemplate
import org.springframework.boot.test.web.server.LocalServerPort
import org.springframework.test.web.servlet.MockMvc
import org.junit.jupiter.api.Assertions.assertFalse
import org.junit.jupiter.api.Assertions.assertTrue
import org.assertj.core.api.Assertions.assertThat
import org.hamcrest.Matchers
import org.springframework.http.MediaType
import org.springframework.test.web.servlet.get
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders
import org.springframework.test.web.servlet.result.MockMvcResultMatchers

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@AutoConfigureMockMvc
class ServiceControllerTest @Autowired constructor(
    val serviceController: ServiceController,
    val restTemplate: TestRestTemplate,
    val serviceComponent: ServiceComponent,
    val mockMvc: MockMvc
) {
    @LocalServerPort
    private val port =0

    private val localhost = "http://localhost:"
    private val localhostService = localhost+port+"/service"
    private val localhostServiceId = localhostService+"/1"
    private val localhostServiceByName = localhostService+"/name/Puppy"
    private val serviceUrl = "http://localhost:8080/service"
    companion object{
        private val serviceCreate = """{"name": "Test","description": "Test","price": 10.1}"""
        private val serviceUpdateEnd = """ ,"name": "TestUpdate","description": "TestUpdate","price": 10.1}"""
        private val serviceUpdateStart ="""{"id": """
    }

    @Test
    fun contextLoad(){
        assertThat(serviceController).isNotNull()
        assertThat(serviceComponent).isNotNull()
    }
        @Test
    fun findAll() {
            assertTrue(
                restTemplate
                    .getForObject(localhost+port+"/service",String::class.java)
                    .toString()
                    .contains("Puppy")
            )
            assertFalse(
                restTemplate
                    .getForObject(localhost+port+"/service",String::class.java)
                    .toString()
                    .contains("adsfasdfgsdfghfsdgyher")
            )
            mockMvc.perform(MockMvcRequestBuilders.get(localhost+port+"/service"))
                .andExpect(MockMvcResultMatchers.jsonPath("$", Matchers.hasSize<String>(11)))
        }

    @Test
    fun findById() {
        mockMvc.get(localhostServiceId).andExpect {
            content {
                string("""{"id":1,"name":"Pet Wellness Exams","description":"Pet Wellness Exams are a key component of your petâs preventative care plan and long-term health and happiness. An annual wellness visit is a checkup for your pet that includes a nose-to-tail physical examination and a discussion of your petâs lifestyle and home environment. Your veterinarian will also ask questions about your petâs nutrition and behavior and address any concerns that you may have. Your veterinarian may recommend additional diagnostic testing and vaccinations (for an additional cost).","price":10.1}""")
            }
        }
    }

    @Test
    fun findByName() {
        mockMvc.get(localhostServiceByName).andExpect {
            content {
                string("""[{"id":2,"name":"Puppy and Kitten Wellness Exams & Vaccination","description":"We know how exciting it is for pet parents to have a new puppy or kitten in the house and thatâs why weâve created a vet visit just for them! Our in-home Wellness Exams and Vaccinations are at the heart of a happy, healthy (and growing) pet and provide essential preventative care. We offer all the core vaccines and non-core âlifestyleâ vaccines that your puppy or kitten will need during their first year of life.","price":13.5}]""")
            }
        }
    }

    @Test
    fun commonTest(){

        val numberServices = serviceComponent.findAll().size

        mockMvc.perform(
            MockMvcRequestBuilders
                .post(serviceUrl)
                .content(serviceCreate)
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON)
        )
            .andExpect(MockMvcResultMatchers.status().isCreated())
            .andExpect(MockMvcResultMatchers.jsonPath("$.id").exists())
        assertEquals(numberServices+1, serviceComponent.findAll().size)


        val id = serviceComponent.findAll().maxBy {it.id}.id
        val serviceFromTable = serviceComponent.findById(id)
        assertEquals(serviceFromTable.id, id)
        assertEquals(serviceFromTable.name, "Test")
        assertEquals(serviceFromTable.description, "Test")
        assertEquals(serviceFromTable.price, 10.1)

        mockMvc.perform(
            MockMvcRequestBuilders
                .put(serviceUrl+"/$id")
                .content(serviceUpdateStart +id+ serviceUpdateEnd)
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON)
        )

        val serviceFromTableUpdate = serviceComponent.findById(id)
        assertEquals(serviceFromTableUpdate.id, id)
        assertEquals(serviceFromTableUpdate.id, id)
        assertEquals(serviceFromTableUpdate.name, "TestUpdate")
        assertEquals(serviceFromTableUpdate.description, "TestUpdate")
        assertEquals(serviceFromTableUpdate.price, 10.1)
        assertEquals(serviceComponent.findAll().size, numberServices+1)

        mockMvc.perform(
            MockMvcRequestBuilders
                .delete(serviceUrl+"/$id")
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON))
            .andExpect(MockMvcResultMatchers.status().isOk())
        assertEquals(serviceComponent.findAll().size, numberServices)
    }
}