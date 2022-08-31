package br.com.crud.request

import br.com.crud.model.User
import javax.validation.constraints.NotBlank

data class UpdateUserRequest(
    @field:NotBlank
    val name: String,
    @field:NotBlank
    val document: String
)

fun UpdateUserRequest.toEntity(user: User): User {
    user.name = name
    user.document = document
    return user
}