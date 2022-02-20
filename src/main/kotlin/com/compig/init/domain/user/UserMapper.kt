package com.compig.init.domain.user

import com.compig.init.domain.user.dto.UserSignUp
import com.compig.init.domain.user.dto.UserUpdate
import com.compig.init.domain.user.entity.User
import org.mapstruct.Mapper
import org.mapstruct.NullValuePropertyMappingStrategy
import org.mapstruct.factory.Mappers

@Mapper(componentModel = "spring",
    uses = [UserMapper::class],
    nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.SET_TO_DEFAULT)
interface UserMapper {
    companion object {
        val converter: UserMapper = Mappers.getMapper(UserMapper::class.java)
    }

    fun userSignUpReqToEntity(user: UserSignUp.UserSignUpReq): User
    fun entityToUserSignUpRep(user: User): UserSignUp.UserSignUpRep
    fun entityToUserUpdateRep(user: User): UserUpdate.UserUpdateRep
}