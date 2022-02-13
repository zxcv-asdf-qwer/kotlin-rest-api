package com.compig.init.domain.user

import com.compig.init.common.config.logger
import com.compig.init.domain.user.dto.UserLogin
import com.compig.init.domain.user.dto.UserSignUp
import lombok.extern.slf4j.Slf4j
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.*

@RestController
@Slf4j
@CrossOrigin
class UserController(
    private val userService: UserService
) {

    @GetMapping("/index")
    private fun getIndexPage(): ResponseEntity<User> {
        logger().info("한글 됨?")
        return ResponseEntity.ok().body(null)
    }

    @PostMapping("/signUp")
    fun signUp(@RequestBody userSignUpReq: UserSignUp.UserSignUpReq): ResponseEntity<UserSignUp.UserSignUpRep> {
        return ResponseEntity.ok(
            userService.createUser(userSignUpReq)
        )
    }

    @PostMapping("/login")
    fun signUp(@RequestBody userLoginReq: UserLogin.UserLoginReq): ResponseEntity<UserLogin.UserLoginRep> {
        return ResponseEntity.ok(
            userService.login(userLoginReq)
        )
    }

}