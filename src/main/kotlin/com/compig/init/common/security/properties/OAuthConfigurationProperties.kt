package com.compig.init.common.security.properties

import org.springframework.boot.context.properties.ConfigurationProperties
import org.springframework.boot.context.properties.ConstructorBinding

@ConstructorBinding
@ConfigurationProperties(prefix = "spring.security.oauth2.client.provider")
data class OAuthConfigurationProperties(
    val clientId: String,
    val clientSecret: String,
    val accessTokenValiditySeconds: Int,
    val refreshTokenValiditySeconds: Int,
)