package com.compig.init.common.exception.exceptions

import com.compig.init.common.exception.ExceptionProperty
import org.springframework.http.HttpStatus


enum class GlobalExceptionErrorCode(
    override val serverMessage: String,
    override val clientMessage: String,
    override val httpStatus: HttpStatus,
) :
    ExceptionProperty {
    REQUEST_NOT_FOUND("Cannot Find Valid Controlelr", "적절한 컨트롤러를 찾지 못했습니다.", HttpStatus.BAD_REQUEST)
}

