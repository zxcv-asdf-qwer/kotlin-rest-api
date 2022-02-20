package com.compig.init.domain.user.dto

import com.compig.init.common.annotation.NoArg
import com.compig.init.domain.user.enumm.UserStatus

class UserSignUp {
    @NoArg
    data class UserSignUpReq(
        var userEmail: String,
        var userLastName: String? = "",
        var userFirstName: String? = "",
        var userPassword: String,
        var userBirth: String? = "",
        var userSex: String? = "",
        var userStatus: UserStatus = UserStatus.USE,
        var etc: String? = "",
        var regUserId: Long?,
        var regIp: String? = "",
    )

    @NoArg
    data class UserSignUpRep(
        var userEmail: String,
        var userLastName: String? = "",
        var userFirstName: String? = "",
        var userBirth: String? = "",
        var userSex: String? = "",
        var userStatus: UserStatus,
        var etc: String? = "",
    )

}
