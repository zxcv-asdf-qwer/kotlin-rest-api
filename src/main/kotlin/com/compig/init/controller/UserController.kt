package com.compig.init.controller

import com.compig.init.common.logger
import com.compig.init.domain.User
import com.compig.init.repository.UserRepository
import lombok.extern.slf4j.Slf4j
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.*
import java.net.URI

@RestController
@Slf4j
@CrossOrigin
class UserController(private var userRepository: UserRepository) {

    @GetMapping("/index")
    private fun getIndexPage(): ResponseEntity<User> {
        logger().info("한글 됨?")
        return ResponseEntity.ok().body(userRepository.findByUserSeqId(1L))
    }

    @PostMapping("/signUp")
    fun signUp(@RequestBody user: User): ResponseEntity.BodyBuilder {
        userRepository.save(user)

        return ResponseEntity.created(URI("tempUri"))
    }

}