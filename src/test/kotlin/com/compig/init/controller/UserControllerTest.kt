package com.compig.init.controller

import org.junit.jupiter.api.*
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Assertions.assertNotNull
import org.junit.jupiter.api.extension.ExtendWith
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.boot.test.web.client.TestRestTemplate
import org.springframework.boot.test.web.client.getForEntity
import org.springframework.boot.test.web.client.postForEntity
import org.springframework.http.*
import org.springframework.test.context.ActiveProfiles
import org.springframework.test.context.junit.jupiter.SpringExtension

@ExtendWith(SpringExtension::class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@TestMethodOrder(MethodOrderer.OrderAnnotation::class)
@ActiveProfiles("test")
internal class UserControllerTest(@Autowired private val restTemplate: TestRestTemplate) {
    @AfterEach
    fun tearDown() {

    }
//
//    @Test
//    @Order(1)
//    fun `ping restricted`() { restTemplate.getForEntity<String>("/api/v1/restricted").also {
//            assertNotNull(it)
//            assertEquals(HttpStatus.FORBIDDEN, it.statusCode)
//        }
//    }

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