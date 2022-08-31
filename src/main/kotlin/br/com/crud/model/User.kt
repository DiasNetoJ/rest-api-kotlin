package br.com.crud.model

import org.springframework.data.annotation.CreatedDate
import org.springframework.data.annotation.LastModifiedDate
import org.springframework.data.jpa.domain.support.AuditingEntityListener
import java.time.LocalDateTime
import javax.persistence.*

@Entity
@EntityListeners(AuditingEntityListener::class)
class User (
    @Column(nullable = false)
    var name: String,
    @Column
    var document: String,
    @Column(nullable = false, updatable = false)
    @CreatedDate
    var createdAt: LocalDateTime? = null,
    @LastModifiedDate
    var modifiedAt: LocalDateTime? = null,
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    var id: Int? = null
)