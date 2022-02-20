package com.compig.init.common.exception

import java.time.OffsetDateTime

data class ErrorResponse(
    var code: String,
    var message: String,
    var timestamp: OffsetDateTime = OffsetDateTime.now(),
    var path: String,
)