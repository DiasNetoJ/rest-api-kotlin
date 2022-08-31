package br.com.crud.service

import br.com.crud.exception.UserNotFoundException
import br.com.crud.model.User
import br.com.crud.repository.UserRepository
import br.com.crud.request.CreateUserRequest
import br.com.crud.request.UpdateUserRequest
import br.com.crud.request.toEntity
import br.com.crud.response.UserResponse
import br.com.crud.response.toResponse
import org.hibernate.ObjectNotFoundException
import org.springframework.stereotype.Service

@Service
class UserService(private val userRepository: UserRepository) {

    fun save(createUserRequest: CreateUserRequest): UserResponse {
        val user = createUserRequest.toEntity()
        return userRepository.save(user).toResponse()
    }

    fun update(id: Int, updateUserRequest: UpdateUserRequest): UserResponse {
        val userSaved = get(id)
        val user = updateUserRequest.toEntity(userSaved)
        return userRepository.save(user).toResponse()
    }

    fun get(id: Int): User {
        return userRepository.findById(id).orElseThrow { UserNotFoundException(id) }
    }

    fun delete(id: Int) {
        get(id)
        userRepository.deleteById(id)
    }
}