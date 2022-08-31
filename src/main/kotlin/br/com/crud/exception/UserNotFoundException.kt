package br.com.crud.exception

import org.springframework.http.HttpStatus
import org.springframework.web.bind.annotation.ResponseStatus

@ResponseStatus(value= HttpStatus.NOT_FOUND, reason="No such User")
class UserNotFoundException(id: Int) : Exception("User $id not found")