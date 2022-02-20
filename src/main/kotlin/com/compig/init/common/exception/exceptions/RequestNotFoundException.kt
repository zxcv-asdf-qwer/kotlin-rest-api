package com.compig.init.common.exception.exceptions

import com.compig.init.common.exception.GlobalException

class RequestNotFoundException private constructor() : GlobalException(GlobalExceptionErrorCode.REQUEST_NOT_FOUND) {
    companion object {
        @JvmField
        val EXCEPTION = RequestNotFoundException()
    }
}