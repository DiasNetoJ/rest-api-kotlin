package br.com.crud

import br.com.crud.model.User
import br.com.crud.repository.UserRepository
import br.com.crud.request.CreateUserRequest
import br.com.crud.request.UpdateUserRequest
import com.fasterxml.jackson.databind.ObjectMapper
import org.assertj.core.api.Assertions.assertThat
import org.hamcrest.CoreMatchers.`is`
import org.hamcrest.CoreMatchers.notNullValue
import org.hamcrest.core.StringContains
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.http.MediaType
import org.springframework.test.web.servlet.MockMvc
import org.springframework.test.web.servlet.request.MockHttpServletRequestBuilder
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders
import org.springframework.test.web.servlet.result.MockMvcResultMatchers.*


@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@AutoConfigureMockMvc
class UserResourceITCase {
    @Autowired
    lateinit var mockMvc: MockMvc
    @Autowired
    lateinit var mapper: ObjectMapper
    @Autowired
    lateinit var userRepository: UserRepository


    @Test
    fun contextLoads() {
        assertThat(mockMvc).isNotNull
        assertThat(mapper).isNotNull
        assertThat(userRepository).isNotNull
    }

    @BeforeEach
    fun cleanDataBase() {
        userRepository.deleteAll()
    }

    @Test
    fun `when getting an user is successful`() {
        val insertedUser = User("Jose Dias Neto", "https://www.linkedin.com/in/diasnetoj/")
        userRepository.save(insertedUser)
        val mockRequest: MockHttpServletRequestBuilder = MockMvcRequestBuilders.get("/users/${insertedUser.id}")
            .contentType(MediaType.APPLICATION_JSON)
            .accept(MediaType.APPLICATION_JSON)

        mockMvc.perform(mockRequest)
            .andExpect(status().isOk)
            .andExpect(content().contentType(MediaType.APPLICATION_JSON))
            .andExpect(jsonPath("$", notNullValue()))
            .andExpect(jsonPath("$.name", `is`(insertedUser.name)))
            .andExpect(jsonPath("$.document", `is`(insertedUser.document)))
    }

    @Test
    fun `should return not found when getting nonexistent user`() {
        val mockRequest: MockHttpServletRequestBuilder = MockMvcRequestBuilders.get("/users/1")
            .contentType(MediaType.APPLICATION_JSON)
            .accept(MediaType.APPLICATION_JSON)

        mockMvc.perform(mockRequest)
            .andExpect(status().isNotFound)
    }

    @Test
    fun `should return not found when try update nonexistent user`() {
        val record = UpdateUserRequest("Jose Dias Neto Alterado", "https://github.com/diasnetoj/")
        val mockRequest: MockHttpServletRequestBuilder = MockMvcRequestBuilders.put("/users/1")
            .contentType(MediaType.APPLICATION_JSON)
            .accept(MediaType.APPLICATION_JSON)
            .content(this.mapper.writeValueAsString(record))

        mockMvc.perform(mockRequest)
            .andExpect(status().isNotFound)
    }

    @Test
    fun `should return not found when try delete nonexistent user`() {
        val mockRequest: MockHttpServletRequestBuilder = MockMvcRequestBuilders.delete("/users/1")
            .contentType(MediaType.APPLICATION_JSON)
            .accept(MediaType.APPLICATION_JSON)

        mockMvc.perform(mockRequest)
            .andExpect(status().isNotFound)
    }

    @Test
    fun `when create user is successful`() {
        val record = CreateUserRequest("Jose Dias Neto", "https://www.linkedin.com/in/diasnetoj/")

        val mockRequest: MockHttpServletRequestBuilder = MockMvcRequestBuilders.post("/users")
            .contentType(MediaType.APPLICATION_JSON)
            .accept(MediaType.APPLICATION_JSON)
            .content(this.mapper.writeValueAsString(record))

        mockMvc.perform(mockRequest)
            .andExpect(status().isCreated)
            .andExpect(header().string("Location", StringContains(false,"/users/")))
            .andExpect(content().contentType(MediaType.APPLICATION_JSON))
            .andExpect(jsonPath("$", notNullValue()))
            .andExpect(jsonPath("$.name", `is`(record.name)))
            .andExpect(jsonPath("$.document", `is`(record.document)))
    }

    @Test
    fun `when update user is successful`() {
        val insertedUser = User("Jose Dias Neto", "https://www.linkedin.com/in/diasnetoj/")
        userRepository.save(insertedUser)

        val record = UpdateUserRequest("Jose Dias Neto Alterado", "https://github.com/diasnetoj/")

        val mockRequest: MockHttpServletRequestBuilder = MockMvcRequestBuilders.put("/users/${insertedUser.id}")
            .contentType(MediaType.APPLICATION_JSON)
            .accept(MediaType.APPLICATION_JSON)
            .content(this.mapper.writeValueAsString(record))

        mockMvc.perform(mockRequest)
            .andExpect(status().isOk)
            .andExpect(content().contentType(MediaType.APPLICATION_JSON))
            .andExpect(jsonPath("$", notNullValue()))
            .andExpect(jsonPath("$.name", `is`(record.name)))
            .andExpect(jsonPath("$.document", `is`(record.document)))
    }

    @Test
    fun `when delete user is successful`() {
        val insertedUser = User("Jose Dias Neto", "https://www.linkedin.com/in/diasnetoj/")
        userRepository.save(insertedUser)

        val mockRequest: MockHttpServletRequestBuilder = MockMvcRequestBuilders.delete("/users/${insertedUser.id}")
            .contentType(MediaType.APPLICATION_JSON)
            .accept(MediaType.APPLICATION_JSON)

        mockMvc.perform(mockRequest)
            .andExpect(status().isNoContent)
    }

    @Test
    fun `should return bad request when name or document are blank`() {
        val record = CreateUserRequest("", "")

        val mockRequest: MockHttpServletRequestBuilder = MockMvcRequestBuilders.post("/users")
            .contentType(MediaType.APPLICATION_JSON)
            .accept(MediaType.APPLICATION_JSON)
            .content(this.mapper.writeValueAsString(record))

        mockMvc.perform(mockRequest)
            .andExpect(status().isBadRequest)
    }
}