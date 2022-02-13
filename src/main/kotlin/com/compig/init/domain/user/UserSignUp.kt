package com.compig.init.domain.user

import com.compig.init.common.UserStatus
import lombok.NoArgsConstructor

class UserSignUp {
    data class UserSignUpReq(
        var userEmail: String = "",
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

    @NoArgsConstructor
    class UserSignUpRep(
        private var userEmail: String = "",
        private var userLastName: String = "",
        private var userFirstName: String = "",
        private var userPassword: String = "",
        private var userBirth: String = "",
        private var userSex: String = "",
        private var userStatus: UserStatus,
        private var etc: String = "",
    )

}
