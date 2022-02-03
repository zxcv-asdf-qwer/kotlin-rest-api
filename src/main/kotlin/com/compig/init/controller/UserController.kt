package com.compig.init.controller

import com.compig.init.common.logger
import com.compig.init.domain.User
import com.compig.init.repository.UserRepository
import lombok.extern.slf4j.Slf4j
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RestController
import java.util.*

@RestController
@Slf4j
class UserController(private var userRepository: UserRepository) {

    @GetMapping("/index")
    private fun getIndexPage(): ResponseEntity<User> {
        logger().info("한글 됨?")
        return ResponseEntity.ok().body(userRepository.findByUserSeqId(1L));
        //val falseLoginForm:Map = hashMapOf("username" to "john.doe", "password" to "wrongpassword")
    }

    @PostMapping("/sign")
    fun signUp(@RequestBody user: User): User =
        userRepository.save(user)
}