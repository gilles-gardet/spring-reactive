package com.ggardet.reactive.model

import org.springframework.data.annotation.CreatedDate
import org.springframework.data.annotation.Id
import org.springframework.data.annotation.LastModifiedDate
import org.springframework.data.relational.core.mapping.Table
import java.time.LocalDateTime

@Table
data class Person(
    @Id
    val id: Long? = null,
    val name: String? = null,
    @CreatedDate
    val created: LocalDateTime? = null,
    @LastModifiedDate
    val updated: LocalDateTime? = null
)
