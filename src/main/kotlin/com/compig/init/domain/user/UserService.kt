package com.compig.init.domain.user

import com.compig.init.domain.user.dto.UserSignUp
import org.hibernate.DuplicateMappingException
import org.modelmapper.ModelMapper
import org.springframework.security.crypto.password.PasswordEncoder
import org.springframework.stereotype.Service
import javax.transaction.Transactional

@Service
@Transactional
class UserService(
    private val passwordEncoder: PasswordEncoder,
    private val userRepository: UserRepository,
    private val modelMapper: ModelMapper,

    ) {
    fun createUserModelMapper(userSignUpReq: UserSignUp.UserSignUpReq): UserSignUp.UserSignUpRep {
        val user: User = modelMapper.map(userSignUpReq, User::class.java)

        if (existUser(userSignUpReq.userEmail)) {
            throw DuplicateMappingException(
                DuplicateMappingException.Type.ENTITY,
                "${userSignUpReq.userEmail} Duplicated."
            )
        }
        userSignUpReq.userPassword = passwordEncoder.encode(userSignUpReq.userPassword)

        userRepository.save(user)

        return modelMapper.map(user, UserSignUp.UserSignUpRep::class.java)
    }

    fun createUser(userSignUpReq: UserSignUp.UserSignUpReq): UserSignUp.UserSignUpRep {
        val user: User = UserMapper.converter.reqToEntity(userSignUpReq)
        if (existUser(userSignUpReq.userEmail)) {
            throw DuplicateMappingException(
                DuplicateMappingException.Type.ENTITY,
                "${userSignUpReq.userEmail} Duplicated."
            )
        }
        userSignUpReq.userPassword = passwordEncoder.encode(userSignUpReq.userPassword)

        userRepository.save(user)

        return UserMapper.converter.entityToRep(user)
    }

    fun existUser(userEmail: String): Boolean {
        userRepository.findByUserEmail(userEmail)?.let {
            return false
        } ?: return true
    }
}