package com.compig.init.domain
import org.hibernate.annotations.CreationTimestamp
import org.hibernate.annotations.UpdateTimestamp
import java.sql.Timestamp
import javax.persistence.*

@Entity
@Table(name = "TUSER")
class User(

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    var userSeqId: Long? = null,
    var userEmail: String = "",
    var userLastName: String = "",
    var userFirstName: String = "",
    var userPassword: String = "",
    var userBirth: String = "",
    var userSex: String = "",
    var userStatus: String = "",
    var etc: String = "",
    @CreationTimestamp
    var regDate: Timestamp? = null,
    var regUserId: String = "",
    var regIp: String = "",
    @UpdateTimestamp
    var modifyDate: Timestamp? = null,
    var modifyUserId: String = "",
    var modifyIp: String = ""

)

