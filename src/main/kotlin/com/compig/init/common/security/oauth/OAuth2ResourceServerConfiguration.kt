@file:Suppress("DEPRECATION")

package com.compig.init.common.security.oauth

import com.compig.init.common.security.jwt.JwtAuthenticationFilter
import com.compig.init.common.security.jwt.JwtTokenProvider
import org.springframework.context.annotation.Configuration
import org.springframework.security.config.annotation.web.builders.HttpSecurity
import org.springframework.security.config.http.SessionCreationPolicy
import org.springframework.security.oauth2.config.annotation.web.configuration.EnableResourceServer
import org.springframework.security.oauth2.config.annotation.web.configuration.ResourceServerConfigurerAdapter
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter
import org.springframework.web.cors.CorsConfiguration

@Configuration
@EnableResourceServer//API 서버를 설정
class OAuth2ResourceServerConfiguration(
    private val jwtTokenProvider: JwtTokenProvider,
) : ResourceServerConfigurerAdapter() {

    override fun configure(http: HttpSecurity) {
        http
            .httpBasic {
                it.disable() //REST API만 고려, 기본 설정해제
            }
            .csrf().disable() //csrf 보안토큰 disable 처리
            .sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS) //토큰 기반 인증이므로 세션 사용 안함
            .and()
            .authorizeRequests {
                it.antMatchers("/api/**").authenticated()
                it.antMatchers(
                    "/index",
                    "/signUp",
                    "/login/**",
                    "/logout/**",
                    "/oauth/**"
                ).permitAll()
                it.anyRequest()
                    .permitAll()//로그인, 회원가입은 누구나 접근 가능
            }//요청에 대한 사용권한 체크
            .cors {
                it.configurationSource {
                    val configuration = CorsConfiguration()
                    configuration.allowCredentials = true
                    configuration.allowedOrigins = listOf("http://localhost:13001")
                    configuration.allowedMethods = listOf("GET", "POST", "PATCH", "DELETE", "OPTIONS")
                    configuration.maxAge = 3600
                    configuration
                }
            }
            .addFilterBefore(
                JwtAuthenticationFilter(jwtTokenProvider),
                UsernamePasswordAuthenticationFilter::class.java
            )
    }
}