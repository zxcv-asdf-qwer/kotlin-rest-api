package com.compig.init.domain.user.dto

import com.compig.init.common.enumm.UserStatus
import com.compig.init.common.config.annotation.NoArg

class UserSignUp {
    @NoArg
    data class UserSignUpReq(
        var userEmail: String,
        var userLastName: String = "",
        var userFirstName: String = "",
        var userPassword: String = "",
        var userBirth: String = "",
        var userSex: String = "",
        var userStatus: UserStatus,
        var etc: String = "",
        var regUserId: Long,
        var regIp: String = "",
    )

    @NoArg
    data class UserSignUpRep(
        var userEmail: String,
        var userLastName: String = "",
        var userFirstName: String = "",
        var userPassword: String = "",
        var userBirth: String = "",
        var userSex: String = "",
        var userStatus: UserStatus,
        var etc: String = "",
    )

}
