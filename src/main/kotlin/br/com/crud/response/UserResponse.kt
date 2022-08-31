package br.com.crud.response

import br.com.crud.model.User
import java.time.LocalDateTime

data class UserResponse(
    val id: Int,
    val name: String,
    val document: String,
    val createdAt: LocalDateTime,
    val modifiedAt: LocalDateTime
)

fun User.toResponse() = UserResponse(
    id = id!!,
    name = name,
    document = document,
    createdAt = createdAt!!,
    modifiedAt = modifiedAt!!
)