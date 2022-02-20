package com.compig.init.common.exception.dto

import com.compig.init.common.exception.GlobalException
import org.springframework.web.context.request.ServletWebRequest
import org.springframework.web.context.request.WebRequest
import java.time.OffsetDateTime

data class ErrorResponse(
    var message: String,
    var timestamp: OffsetDateTime = OffsetDateTime.now(),
    var path: String,
) {
    companion object {
        fun of(e: GlobalException, er: WebRequest): ErrorResponse {
            return ErrorResponse(
                message = e.clientMessage,
                path = (er as ServletWebRequest).request.requestURI)
        }

        fun of(e: Exception, er: WebRequest): ErrorResponse {
            return ErrorResponse(
                message = e.message!!,
                path = (er as ServletWebRequest).request.requestURI)
        }
    }
}