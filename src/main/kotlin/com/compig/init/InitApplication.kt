package com.compig.init

import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.runApplication
import org.springframework.data.jpa.repository.config.EnableJpaAuditing

@EnableJpaAuditing
@SpringBootApplication
class InitApplication

fun main(args: Array<String>) {
    runApplication<InitApplication>(*args)
}
