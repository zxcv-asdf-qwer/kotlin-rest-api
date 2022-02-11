package com.compig.init.controller

import com.compig.init.common.logger
import org.junit.jupiter.api.*
import org.junit.jupiter.api.extension.ExtendWith
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.http.MediaType
import org.springframework.test.context.ActiveProfiles
import org.springframework.test.context.junit.jupiter.SpringExtension
import org.springframework.test.web.servlet.MockMvc
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post
import org.springframework.test.web.servlet.result.MockMvcResultHandlers.print
import org.springframework.test.web.servlet.result.MockMvcResultMatchers.status

@ExtendWith(SpringExtension::class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@TestMethodOrder(MethodOrderer.OrderAnnotation::class)
@ActiveProfiles("test")
@AutoConfigureMockMvc
//@Transactional
internal class UserControllerTest(@Autowired private val mockMvc: MockMvc) {

    @AfterEach
    fun tearDown() {

    }

    @Test
    @Order(1)
    fun signUp() {
        mockMvc.perform(
            post("/signUp")
                .content("{\"userLastName\":\"HyeYoung\",\"userFirstName\":\"Chung\"}")
                .contentType(MediaType.APPLICATION_JSON)
        )

            //기대하는 응답코드
            .andExpect(status().isCreated)
            //기대하는 결과값
            //.andExpect(content().string("{\"responseCode\":200,\"responseMsg\":\"success\"}"))
            //결과 출력
            .andDo { print() }

        logger().info("signUp????되니???????????")
        //assertEquals(HttpStatus.CREATED, it.statusCode)
    }

//    @Test
//    @Order(2)
//    fun `failed login with wrong password`() {
//
//        val falseLoginForm = hashMapOf("username" to "john.doe", "password" to "wrongpassword")
//        try {
//            restTemplate.postForEntity<Any>("/login", falseLoginForm).also {
//                assertNotNull(it)
//                assertEquals(HttpStatus.UNAUTHORIZED, it.statusCode)
//            }
//        } catch (e: Exception) {
//            print("Fixme RestTemplate with HttpStatus.UNAUTHORIZED result")
//        }
//    }
}