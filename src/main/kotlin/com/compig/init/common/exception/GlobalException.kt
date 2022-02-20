package com.compig.init.common.exception

import org.springframework.http.HttpStatus

open class GlobalException(private val property: ExceptionProperty): RuntimeException() {
    val httpStatus: HttpStatus
        get() = property.httpStatus

    override val message
        get() = property.serverMessage

    val clientMessage
        get() = property.clientMessage
}