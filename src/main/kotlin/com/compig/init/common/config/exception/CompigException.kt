package com.compig.init.common.config.exception

import org.springframework.beans.factory.annotation.Autowired
import org.springframework.http.HttpStatus
import java.lang.RuntimeException
import java.time.OffsetDateTime


class CompigException(@Autowired val restException: RuntimeException){

    private val serialVersionUID = 123432423L
    private var httpStatus: HttpStatus? = null
    private var code: String? = null
    private var message: String? = null
    private var timestamp: OffsetDateTime? = OffsetDateTime.now()

    //internal server error log && client response
    fun CompigException(errorMessage: String?, ee: Errors) {
        this.
        this.code = ee.code
        this.message = ee.message
    }


}