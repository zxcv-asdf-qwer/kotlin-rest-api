package com.compig.init.common.exception.exceptions

import com.compig.init.common.exception.GlobalException

internal class InvalidInputValueException private constructor() : GlobalException(GlobalExceptionErrorCode.INVALID_INPUT_VALUE) {
    companion object {
        @JvmField
        val EXCEPTION = InvalidInputValueException()
    }
}