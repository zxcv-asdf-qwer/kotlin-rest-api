package com.compig.init.common.security.auth

import com.compig.init.domain.user.entity.User
import org.springframework.security.core.GrantedAuthority
import org.springframework.security.core.userdetails.UserDetails

class AuthDetails(private val user: User, private val authorities: MutableCollection<out GrantedAuthority>) : UserDetails {
    //계정이 갖고 있는 권한 목록을 리턴한다.
    override fun getAuthorities(): MutableCollection<out GrantedAuthority> {
        return authorities
    }

    override fun getPassword(): String {
        return user.userPassword
    }

    override fun getUsername(): String {
        return user.userEmail
    }

    //계정이 만료되지 않았는지를 리턴 true: 만료되지 않음을 의미
    override fun isAccountNonExpired(): Boolean {
        return true
    }

    //계정이 잠겨있지 않은지를 리턴한다. true: 계정이 잠겨있지 않음을 의미
    override fun isAccountNonLocked(): Boolean {
        return true
    }

    //계정의 패스워드가 만료되지 않았는지를 리턴한다. true: 패스워드가 만료되지 않음을 의미
    override fun isCredentialsNonExpired(): Boolean {
        return true
    }

    //계정이 사용가능한 계정인지를 리턴한다. true: 사용가능한 계정인지를 의미
    override fun isEnabled(): Boolean {
        return true
    }
}