package com.compig.init.common.security.auth

import com.compig.init.common.security.exceptions.UserNotFoundException
import com.compig.init.domain.user.entity.User
import com.compig.init.domain.user.entity.UserRepository
import org.springframework.security.core.userdetails.UserDetails
import org.springframework.security.core.userdetails.UserDetailsService
import org.springframework.stereotype.Service


@Service
class AuthDetailsService(private val userRepository: UserRepository) : UserDetailsService {
    override fun loadUserByUsername(username: String): UserDetails {
        val user: User = userRepository.findByUserEmail(username)
            ?: throw UserNotFoundException.EXCEPTION
        return AuthDetails(user = user)
    }

}