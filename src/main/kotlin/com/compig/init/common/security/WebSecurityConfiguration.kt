package com.compig.init.common.security

import com.compig.init.common.security.auth.AuthDetailsService
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.security.authentication.AuthenticationManager
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder
import org.springframework.security.crypto.password.PasswordEncoder

/**
 * passwordEncoder를 만들고, 앞에서 만들어준 JwtAuthenticationFilter를 등록
 *로그인과 회원가입 요청 Url을 제외한 나머지는 인증을 받아야 요청 가능하도록 권한을 설정
 * **/
@Configuration
@EnableGlobalMethodSecurity(prePostEnabled = true)
class WebSecurityConfiguration(
    private val authDetailsService: AuthDetailsService,
) : WebSecurityConfigurerAdapter() {

    override fun configure(auth: AuthenticationManagerBuilder) {
        auth.userDetailsService(authDetailsService)
            .passwordEncoder(this.passwordEncoder())
    }

    @Bean
    fun passwordEncoder(): PasswordEncoder {
        return BCryptPasswordEncoder()
    }

    @Bean
    override fun authenticationManagerBean(): AuthenticationManager {
        return super.authenticationManagerBean()
    }


}