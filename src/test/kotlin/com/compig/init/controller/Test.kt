package com.compig.init.controller

import org.junit.jupiter.api.Test
import org.junit.jupiter.api.extension.ExtendWith
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.test.context.ActiveProfiles
import org.springframework.test.context.junit.jupiter.SpringExtension

@ExtendWith(SpringExtension::class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@ActiveProfiles("test")
@AutoConfigureMockMvc
internal class Test {

    @Test
    fun mainTest() {
        println("----------------------------------------------------------------")
        val genres : Array<String> = mutableListOf("classic", "pop", "classic", "classic", "pop").toTypedArray()
        var plays : IntArray = intArrayOf(500, 600, 150, 800, 2500)
        solution(genres, plays)

        println("----------------------------------------------------------------")

    }
    fun solution(genres: Array<String>, plays: IntArray) =
        genres.indices.groupBy { genres[it] }.toList().sortedByDescending { it.second.sumBy { plays[it] }}

            .map {it.second.sortedByDescending { plays[it] }.take(2)}.flatten().toIntArray()
}