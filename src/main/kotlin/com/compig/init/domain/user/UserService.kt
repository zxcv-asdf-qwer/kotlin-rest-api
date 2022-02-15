package com.compig.init.domain.user

import com.compig.init.common.config.security.JwtTokenProvider
import com.compig.init.domain.user.dto.UserLogin
import com.compig.init.domain.user.dto.UserSignUp
import com.compig.init.domain.user.dto.UserUpdate
import org.hibernate.DuplicateMappingException
import org.modelmapper.ModelMapper
import org.springframework.dao.InvalidDataAccessApiUsageException
import org.springframework.security.crypto.password.PasswordEncoder
import org.springframework.stereotype.Service
import javax.transaction.Transactional

@Service
@Transactional
class UserService(
    private val passwordEncoder: PasswordEncoder,
    private val userRepository: UserRepository,
    private val modelMapper: ModelMapper,
    private val jwtTokenProvider: JwtTokenProvider,

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
        if (existUser(user.userEmail)) {
            throw DuplicateMappingException(
                DuplicateMappingException.Type.ENTITY,
                "${user.userEmail} Duplicated."
            )
        }
        user.userPassword = passwordEncoder.encode(user.userPassword)

        userRepository.save(user)

        return UserMapper.converter.entityToRep(user)
    }

    fun existUser(userEmail: String): Boolean {
        userRepository.findByUserEmail(userEmail)?.let {
            return true
        } ?: return false
    }

    fun findUser(userEmail: String): User {
        return userRepository.findByUserEmail(userEmail) ?: throw NullPointerException("user not found.")
    }

    fun login(userLoginReq: UserLogin.UserLoginReq): UserLogin.UserLoginRep {

        if (!existUser(userLoginReq.userEmail)) {
            throw NullPointerException(
                "${userLoginReq.userEmail} not found."
            )
        }
        val user: User = findUser(userLoginReq.userEmail)

        if (!passwordEncoder.matches(userLoginReq.userPassword, user.password)) {
            throw InvalidDataAccessApiUsageException("invalid password.")
        }

        return UserLogin.UserLoginRep(
            token = jwtTokenProvider.createToken(user.userEmail), user)
    }

    fun userUpdate(userUpdateReq: UserUpdate.UserUpdateReq): UserUpdate.UserUpdateRep {
        val user: User = findUser(userUpdateReq.userEmail)

        val userReq: User = UserMapper.converter.reqToEntity(userUpdateReq)

        user.userPassword = passwordEncoder.encode(userReq.userPassword)

        userRepository.save(user)

        return UserMapper.converter.entityToUserUpdateRep(user)
    }

}