package br.com.crud.request

import br.com.crud.model.User
import javax.validation.constraints.NotBlank

data class CreateUserRequest(
    @field:NotBlank
    val name: String,
    @field:NotBlank
    val document: String
)

fun CreateUserRequest.toEntity() = User(
    name = name,
    document = document
)