package com.compig.init.common.security.exceptions

import com.compig.init.common.exception.GlobalException

internal class UserNotFoundException private constructor() : GlobalException(SecurityExceptionErrorCode.NOT_FOUND_USER_EMAIL) {
    companion object {
        @JvmField
        val EXCEPTION = UserNotFoundException()
    }
}