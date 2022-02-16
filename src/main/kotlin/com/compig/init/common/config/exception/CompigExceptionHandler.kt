package com.compig.init.common.config.exception

import com.compig.init.common.config.logger
import com.compig.init.common.entity.ErrorResponse
import lombok.extern.slf4j.Slf4j
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.ExceptionHandler
import org.springframework.web.bind.annotation.RestControllerAdvice
import java.time.LocalDateTime

@Slf4j
@RestControllerAdvice
class CompigExceptionHandler {

    @ExceptionHandler(value = [Exception::class])
    fun coldbrewHandler(e: Exception): ResponseEntity<ErrorResponse> {
        logPrint(e)
        return ResponseEntity<ErrorResponse>(
            ErrorResponse(code = "brewbrewbrew",
                message = "꽤액!!!!!!!!!!!!!!!!!!!!!!1",
                timestamp = LocalDateTime.now()), HttpStatus.INTERNAL_SERVER_ERROR)
    }
    //TODO Error enum 추가하기
    //TODO gradle multi module
    //TODO ummmmmmmmmmmmmmm..

    private fun logPrint(e: Exception) {
        logger().error("error file : " + e.stackTrace[0].fileName)
        logger().error("error line : " + e.stackTrace[0].lineNumber)
        logger().error("error message : " + e.message)
    }

}