package com.compig.init.domain.user

import com.compig.init.domain.user.dto.UserSignUp
import com.compig.init.domain.user.dto.UserUpdate
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

    fun reqToEntity(user: UserSignUp.UserSignUpReq): User
    fun entityToRep(user: User): UserSignUp.UserSignUpRep
    fun reqToEntity(user: UserUpdate.UserUpdateReq): User
    fun entityToUserUpdateRep(user: User): UserUpdate.UserUpdateRep
}