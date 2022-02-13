package com.compig.init.common.entity

import org.hibernate.annotations.CreationTimestamp
import org.hibernate.annotations.UpdateTimestamp
import org.springframework.data.jpa.domain.support.AuditingEntityListener
import java.sql.Timestamp
import javax.persistence.Column
import javax.persistence.EntityListeners
import javax.persistence.MappedSuperclass

@MappedSuperclass
//해당 클래스에 Auditing 기능을 포함
@EntityListeners(AuditingEntityListener::class)
abstract class BaseTime {
    //@Column에 updatable = false 추가
    //수정되었을 때 날짜가 최신화되지 않게 하기 위해서
    //Jpa Auditing 어노테이션들을 활성화 main kt 에 @EnableJpaAuditing 추가
    // 생성/수정 시간을 자동화하고자 하는 Entity 클래스 BaseTime 상속
    @CreationTimestamp
    @Column(nullable = false, updatable = false)
    var regDate: Timestamp? = null

    @UpdateTimestamp
    @Column(nullable = false)
    var modifyDate: Timestamp? = null
}