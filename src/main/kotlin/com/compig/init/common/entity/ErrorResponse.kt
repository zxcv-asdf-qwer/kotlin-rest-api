package com.compig.init.common.entity

import java.time.LocalDateTime

data class ErrorResponse(
    var code: String,
    var message: String,
    var timestamp: LocalDateTime,
)