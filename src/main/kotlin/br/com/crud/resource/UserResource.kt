package br.com.crud.resource

import br.com.crud.model.User
import br.com.crud.request.CreateUserRequest
import br.com.crud.request.UpdateUserRequest
import br.com.crud.response.UserResponse
import br.com.crud.service.UserService
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.*
import org.springframework.web.servlet.support.ServletUriComponentsBuilder
import javax.validation.Valid

@RequestMapping( "/users")
@RestController
class UserResource(
    @Autowired private val userService: UserService
) {

    @GetMapping("/{id}")
    fun get(@PathVariable("id") id: Int): User = userService.get(id)

    @PostMapping
    fun save(@Valid @RequestBody createUserRequest: CreateUserRequest): ResponseEntity<UserResponse> {
        val userCreated = userService.save(createUserRequest)
        return ResponseEntity.created(ServletUriComponentsBuilder
            .fromCurrentRequest()
            .path("/{id}")
            .buildAndExpand(userCreated.id)
            .toUri()).body(userCreated)
    }

    @PutMapping("/{id}")
    fun update(@PathVariable("id") id: Int, @Valid @RequestBody updateUserRequest: UpdateUserRequest): ResponseEntity<UserResponse> {
        return ResponseEntity.ok(userService.update(id, updateUserRequest))
    }

    @DeleteMapping("/{id}")
    fun delete(@PathVariable("id") id: Int) : ResponseEntity<Void>{
        userService.delete(id)
        return ResponseEntity.noContent().build()
    }
}