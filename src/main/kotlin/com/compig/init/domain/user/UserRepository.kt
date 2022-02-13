package com.compig.init.domain.user

import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.stereotype.Repository

@Repository
interface UserRepository : JpaRepository<User, Long?> {

    fun findByUserSeqId(userSeqId: Long): User?

    fun findByUserEmail(userEmail: String): User?
}
