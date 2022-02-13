package com.compig.init.domain.user

import com.compig.init.common.entity.BaseTime
import org.hibernate.annotations.DynamicInsert
import org.springframework.security.core.GrantedAuthority
import org.springframework.security.core.userdetails.UserDetails
import javax.persistence.*

@Entity
@Table(name = "TUSER")
@DynamicInsert
class User(

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    var userSeqId: Long,
    @Column(nullable = false)
    var userEmail: String,
    var userLastName: String? = "",
    var userFirstName: String? = "",
    @Column(nullable = false)
    var userPassword: String,
    var userBirth: String? = "",
    var userSex: String? = "",
    @Column(nullable = false)
    var userStatus: String,
    var etc: String? = "",
    var regUserId: Long?,
    var regIp: String? = "",
    var modifyUserId: Long?,
    var modifyIp: String? = "",

    ) : BaseTime(), UserDetails {
    override fun getAuthorities(): MutableCollection<out GrantedAuthority>? {
        return null
    }

    override fun getPassword(): String {
        return userPassword
    }

    override fun getUsername(): String {
        return userEmail
    }

    override fun isAccountNonExpired(): Boolean {
        return true
    }

    override fun isAccountNonLocked(): Boolean {
        return true
    }

    override fun isCredentialsNonExpired(): Boolean {
        return true
    }

    override fun isEnabled(): Boolean {
        return true
    }

}

