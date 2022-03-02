package com.compig.init.common.security.jwt

import com.compig.init.common.security.auth.AuthDetailsService
import io.jsonwebtoken.Claims
import io.jsonwebtoken.Jwts
import io.jsonwebtoken.SignatureAlgorithm
import io.jsonwebtoken.io.Decoders
import io.jsonwebtoken.security.Keys
import org.springframework.beans.factory.annotation.Qualifier
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken
import org.springframework.security.core.Authentication
import org.springframework.stereotype.Component
import java.util.*
import javax.annotation.PostConstruct
import javax.crypto.SecretKey
import javax.servlet.http.HttpServletRequest

/**
 * JWT 토큰을 발급하고, 인증 정보를 조회하고, 회원 정보를 추출
 * https://codingdiary99.tistory.com/entry/Spring-boot-Kotlin-Spring-security-JWT-%EB%A1%9C%EA%B7%B8%EC%9D%B8-%EA%B5%AC%ED%98%84%ED%95%98%EA%B8%B0
 * https://samtao.tistory.com/65
 * **/
@Component
//@Qualifier - Spring이 어떤 bean을 주입할지 모름 명시해줌
class JwtTokenProvider(@Qualifier("authDetailsService") private val authDetailsService: AuthDetailsService) {
    // JWT를 생성하고 검증하는 컴포넌트
    //secretKey 글자가 작으면 에러남
    private var secretKey = "coldbrewbrewbrewbrewbrewbrewbrewbrewbrewbrewbrewbrew"

    // 토큰 유효시간 30분
    private val tokenValidTime = 30 * 60 * 1000L

    // 객체 초기화, secretKey를 Base64로 인코딩한다.
    lateinit var key: SecretKey

    //lateinit - var(mutable)에서만 사용이 가능
    @PostConstruct //web server 가 올라갈때 초기화 된다.
    protected fun init() {
        key = Keys.hmacShaKeyFor(Decoders.BASE64.decode(secretKey));
    }

    // JWT 토큰 생성
    fun createToken(userPk: String): String {
        if (!::key.isInitialized) {
            throw NullPointerException("Bannnnnnnnnnnnnnnnn! key is not Initialized")
        }
        val claims: Claims = Jwts.claims().setSubject(userPk) // JWT payload 에 저장되는 정보단위
        claims["userPk"] = userPk // 정보는 key / value 쌍으로 저장된다.
        val now = Date()
        return Jwts.builder()
            .setHeaderParam("typ", "JWT")
            .setClaims(claims) // 정보 저장
            .setIssuedAt(now) // 토큰 발행 시간 정보
            .setExpiration(Date(now.time + tokenValidTime)) // set Expire Time
            .signWith(key, SignatureAlgorithm.HS256) // 사용할 암호화 알고리즘과
            // signature 에 들어갈 secret 값 세팅
            .compact()
    }

    // JWT 토큰에서 인증 정보 조회
    fun getAuthentication(token: String): Authentication {
        val userDetails = authDetailsService.loadUserByUsername(getUserPk(token))
        return UsernamePasswordAuthenticationToken(userDetails, "", userDetails.authorities)
    }

    // 토큰에서 회원 정보 추출
    fun getUserPk(token: String): String {
        return Jwts.parserBuilder().setSigningKey(secretKey).build().parseClaimsJws(token).body.subject
    }

    // Request의 Header에서 token 값을 가져옵니다. "X-AUTH-TOKEN" : "TOKEN값'
    fun resolveToken(request: HttpServletRequest): String? {
        return request.getHeader("Authorization")
    }

    // 토큰의 유효성 + 만료일자 확인
    fun validateToken(jwtToken: String): Boolean {
        return try {
            val claims = Jwts.parserBuilder().setSigningKey(secretKey).build().parseClaimsJws(jwtToken)
            !claims.body.expiration.before(Date())
        } catch (e: Exception) {
            false
        }
    }
}