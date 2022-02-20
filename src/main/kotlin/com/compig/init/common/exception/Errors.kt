package com.compig.init.common.exception

import org.springframework.http.HttpStatus


enum class Errors(var httpStatus: HttpStatus, var code: String, var message: String) {
    BANNNN(HttpStatus.BAD_REQUEST, "BAN-400", "뺴애애액!!!"),
    NOT_FOUND_USER_EMAIL(HttpStatus.BAD_REQUEST, "LOGIN-400", "아이디가 없습니다.")

}
