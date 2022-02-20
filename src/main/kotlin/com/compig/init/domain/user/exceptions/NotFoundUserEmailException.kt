package com.compig.init.domain.user.exceptions

import com.compig.init.common.exception.GlobalException

internal class NotFoundUserEmailException private constructor() : GlobalException(UserExceptionErrorCode.NOT_FOUND_USER_EMAIL) {
    companion object {
        @JvmField
        val EXCEPTION = NotFoundUserEmailException()
    }
}