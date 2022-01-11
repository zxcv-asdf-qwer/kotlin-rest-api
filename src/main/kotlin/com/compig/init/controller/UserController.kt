package com.compig.init.controller

import com.compig.init.common.logger
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.RestController

@RestController
class UserController {

    @GetMapping("/index")
    fun getIndexPage() : String{
        logger().info("한글 됨?")

        return "hello~"
    }
}