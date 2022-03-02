package com.compig.init.common.security.oauth

import com.compig.init.common.security.auth.AuthDetailsService
import com.compig.init.common.security.enumm.OAuthGrantType
import com.compig.init.common.security.properties.OAuthConfigurationProperties
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.security.authentication.AuthenticationManager
import org.springframework.security.crypto.password.PasswordEncoder
import org.springframework.security.oauth2.config.annotation.configurers.ClientDetailsServiceConfigurer
import org.springframework.security.oauth2.config.annotation.web.configuration.AuthorizationServerConfigurerAdapter
import org.springframework.security.oauth2.config.annotation.web.configuration.EnableAuthorizationServer
import org.springframework.security.oauth2.config.annotation.web.configurers.AuthorizationServerEndpointsConfigurer
import org.springframework.security.oauth2.config.annotation.web.configurers.AuthorizationServerSecurityConfigurer
import org.springframework.security.oauth2.provider.token.TokenEnhancer
import org.springframework.security.oauth2.provider.token.TokenEnhancerChain
import org.springframework.security.oauth2.provider.token.TokenStore
import org.springframework.security.oauth2.provider.token.store.JwtAccessTokenConverter
import org.springframework.security.oauth2.provider.token.store.JwtTokenStore
import javax.sql.DataSource

@Configuration
@EnableAuthorizationServer//OAuth2 서버설정
class OAuth2AuthorizationServerConfiguration(
    private val authenticationManager: AuthenticationManager,
    private val passwordEncoder: PasswordEncoder,
    private val oAuthConfigurationProperties: OAuthConfigurationProperties,
    private val authDetailsService: AuthDetailsService,
    private val datasource: DataSource,
) : AuthorizationServerConfigurerAdapter() {

    override fun configure(security: AuthorizationServerSecurityConfigurer) {
        security.passwordEncoder(passwordEncoder)
            .checkTokenAccess("permitAll()")
    }

    override fun configure(clients: ClientDetailsServiceConfigurer) {

        clients.inMemory()
            .withClient(oAuthConfigurationProperties.clientId)
            .secret(passwordEncoder.encode(oAuthConfigurationProperties.clientSecret))
            .authorities("ROLE_USER")
            .scopes("read", "write")
            .authorizedGrantTypes(
                OAuthGrantType.REFRESH_TOKEN.grant,
                OAuthGrantType.PASSWORD.grant,
                OAuthGrantType.AUTHORIZATION_CODE.grant,
                OAuthGrantType.IMPLICIT.grant
            )
            .accessTokenValiditySeconds(oAuthConfigurationProperties.accessTokenValiditySeconds)
            .refreshTokenValiditySeconds(oAuthConfigurationProperties.refreshTokenValiditySeconds)
            .redirectUris("/oauth2/callback")
    }

    override fun configure(endpoints: AuthorizationServerEndpointsConfigurer) {
        val tokenEnhancerChain = TokenEnhancerChain()
        tokenEnhancerChain.setTokenEnhancers(
            listOf(tokenEnhancer(), jwtAccessTokenConverter())
        )
        endpoints.tokenStore(tokenStore())
            .tokenEnhancer(tokenEnhancerChain) //추가한 token정보를 추가
            .userDetailsService(authDetailsService)
            .accessTokenConverter(jwtAccessTokenConverter())
            .reuseRefreshTokens(false)
            .authenticationManager(authenticationManager)
    }

    @Bean
    fun jwtAccessTokenConverter(): JwtAccessTokenConverter {
        val accessTokenConverter = JwtAccessTokenConverter()
        accessTokenConverter.setSigningKey("compig")
        return accessTokenConverter
    }

    @Bean
    fun tokenStore(): TokenStore {
        return JwtTokenStore(jwtAccessTokenConverter())
    }

    @Bean
    fun tokenEnhancer(): TokenEnhancer {
        return CustomTokenEnhancer()
    }
}