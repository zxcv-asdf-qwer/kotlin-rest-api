package com.compig.init.domain.user.exceptions

import com.compig.init.common.exception.ExceptionProperty
import org.springframework.http.HttpStatus

enum class UserExceptionErrorCode(
    override val serverMessage: String,
    override val clientMessage: String,
    override val httpStatus: HttpStatus,
) :
    ExceptionProperty {
    NOT_FOUND_USER_EMAIL("not found user Email.", "아이디가 없습니다.", HttpStatus.BAD_REQUEST)
}