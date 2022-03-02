package com.compig.init.common.security.oauth

import com.compig.init.common.security.auth.AuthDetails
import com.compig.init.domain.user.entity.UserRepository
import lombok.extern.slf4j.Slf4j
import org.springframework.context.annotation.Configuration
import org.springframework.security.oauth2.common.DefaultOAuth2AccessToken
import org.springframework.security.oauth2.common.OAuth2AccessToken
import org.springframework.security.oauth2.provider.OAuth2Authentication
import org.springframework.security.oauth2.provider.token.TokenEnhancer
import java.util.*
import javax.annotation.Resource

@Slf4j
@Configuration
class CustomTokenEnhancer : TokenEnhancer {
    @Resource
    private val userRepository: UserRepository? = null

    override fun enhance(
        accessToken: OAuth2AccessToken,
        authentication: OAuth2Authentication,
    ): OAuth2AccessToken {
        val additionalInfo: MutableMap<String, Any> = HashMap()
        // CustomUserDetails 에서 정보를 가지고 온다
        val principal = authentication.principal as AuthDetails
        val userId = principal.username
        userRepository!!.findByUserEmail(userId)
            .let { user ->
                // 여기서 필요한 정보 추가
                additionalInfo["userSeqId"] = user!!.userSeqId
                additionalInfo["userId"] = userId
                additionalInfo["roleName"] = userRepository!!.findRoleByUserSeqId(user.userSeqId)
            }
        (accessToken as DefaultOAuth2AccessToken).additionalInformation = additionalInfo
        return accessToken
    }
}