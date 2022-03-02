package com.compig.init.common.security.enumm

enum class OAuthGrantType(val grant: String){
    REFRESH_TOKEN("refresh_token"),
    AUTHORIZATION_CODE("authorization_code"),
    PASSWORD("password"),
    IMPLICIT("implicit");
}