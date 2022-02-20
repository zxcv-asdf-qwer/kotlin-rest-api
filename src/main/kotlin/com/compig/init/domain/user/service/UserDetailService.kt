package com.compig.init.domain.user.service

import com.compig.init.domain.user.entity.UserRepository
import org.springframework.data.crossstore.ChangeSetPersister
import org.springframework.security.core.userdetails.UserDetails
import org.springframework.security.core.userdetails.UserDetailsService
import org.springframework.stereotype.Service


@Service
class UserDetailService(private val userRepository: UserRepository) : UserDetailsService {
    override fun loadUserByUsername(username: String): UserDetails {
        return userRepository.findByUserEmail(username)
            ?: throw ChangeSetPersister.NotFoundException()
    }

}