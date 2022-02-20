package com.compig.init.domain.user.dto

import com.compig.init.common.annotation.NoArg
import com.compig.init.domain.user.entity.User

class UserLogin {
    @NoArg
    data class UserLoginReq(
        var userEmail: String,
        var userPassword: String,
    )

    @NoArg
    data class UserLoginRep(
        var token: String,
        var user: User,
    )

}