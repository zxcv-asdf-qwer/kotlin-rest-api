package com.compig.init.domain.user.dto

import com.compig.init.common.config.annotation.NoArg
import com.compig.init.common.enumm.UserStatus

class UserUpdate {

    @NoArg
    data class UserUpdateReq(
        var userEmail: String,
        var userLastName: String? = "",
        var userFirstName: String? = "",
        var userPassword: String,
        var userBirth: String? = "",
        var userSex: String? = "",
        var etc: String? = "",
        var modifyUserId: Long?,
        var modifyIp: String? = "",
    )

    @NoArg
    data class UserUpdateRep(
        var userEmail: String,
        var userLastName: String? = "",
        var userFirstName: String? = "",
        var userBirth: String? = "",
        var userSex: String? = "",
        var userStatus: UserStatus,
        var etc: String? = "",
    )
}