package com.compig.init.common.config.exception


class CompigException(override val message: String? = null, val ee: Errors) : RuntimeException(message)