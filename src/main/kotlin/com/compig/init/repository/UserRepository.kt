package com.compig.init.repository

import com.compig.init.domain.User
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.stereotype.Repository

@Repository
interface UserRepository : JpaRepository<User, Long?> {

    fun findByUserSeqId(userSeqId: Long): User?
}
