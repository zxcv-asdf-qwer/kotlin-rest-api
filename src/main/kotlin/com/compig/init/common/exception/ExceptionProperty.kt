package com.compig.init.common.exception

import org.springframework.http.HttpStatus

interface ExceptionProperty {
    val serverMessage: String
    val clientMessage: String
    val httpStatus: HttpStatus
}