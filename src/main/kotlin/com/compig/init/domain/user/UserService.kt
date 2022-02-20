package com.compig.init.domain.user

import com.compig.init.common.config.exception.CompigException
import com.compig.init.common.config.exception.Errors
import com.compig.init.common.config.security.JwtTokenProvider
import com.compig.init.domain.user.dto.UserLogin
import com.compig.init.domain.user.dto.UserSignUp
import com.compig.init.domain.user.dto.UserUpdate
import com.compig.init.domain.user.entity.User
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
        val user: User = UserMapper.converter.userSignUpReqToEntity(userSignUpReq)
        if (existUser(user.userEmail)) {
            throw DuplicateMappingException(
                DuplicateMappingException.Type.ENTITY,
                "${user.userEmail} Duplicated."
            )
        }
        user.userPassword = passwordEncoder.encode(user.userPassword)

        userRepository.save(user)

        return UserMapper.converter.entityToUserSignUpRep(user)
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
            throw CompigException(
                "${userLoginReq.userEmail} not found.", Errors.NOT_FOUND_USER_EMAIL
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
        with(user) {
            userEmail = userUpdateReq.userEmail
            userLastName = userUpdateReq.userLastName
            userFirstName = userUpdateReq.userFirstName
            userPassword = passwordEncoder.encode(userUpdateReq.userPassword)
            userBirth = userUpdateReq.userBirth
            userSex = userUpdateReq.userSex
            etc = userUpdateReq.etc
            modifyUserId = userUpdateReq.modifyUserId
            modifyIp = userUpdateReq.modifyIp
        }

        userRepository.save(user)

        return UserMapper.converter.entityToUserUpdateRep(user)
    }

}