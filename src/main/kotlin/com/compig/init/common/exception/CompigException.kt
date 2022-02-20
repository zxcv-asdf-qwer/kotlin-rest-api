package com.compig.init.common.exception


class CompigException(override val message: String? = null, val ee: Errors) : RuntimeException(message)