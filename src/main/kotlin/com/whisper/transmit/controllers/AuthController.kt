package com.whisper.transmit.controllers

import com.whisper.transmit.models.*
import com.whisper.transmit.repository.GroupRepository
import com.whisper.transmit.repository.UserRepository
import com.whisper.transmit.services.Crud
import jakarta.validation.Valid
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.*

@RestController
class AuthController(
        private val userRepository: UserRepository,
        private val groupRepository: GroupRepository
) {

    @PostMapping("/login")
    fun login(@Valid @RequestBody tLogin: TLogin): ResponseEntity<*>  {
        val user = Crud.find { userRepository.findByNameOrUsernameOrPhone(tLogin.identity, tLogin.identity, tLogin.identity) }
        return ResponseEntity.ok(
                mapOf(
                        "user" to user,
                        "groups" to user.groups,
                        "administering" to user.administrationGroups
                )
        )
    }

    @GetMapping("/users/{id}/groups")
    fun getUserGroups(@PathVariable id: String): ResponseEntity<*> {
        val user = Crud.find { userRepository.findById(id) }
        return ResponseEntity.ok(user.groups)
    }

    @PostMapping("/register")
    fun newUser(@Valid @RequestBody user: User): ResponseEntity<User>  {
        return ResponseEntity.status(201).body(userRepository.save(user))
    }

    @PostMapping("/join-group")
    fun joinGroup(@Valid @RequestBody jlGroup: JLGroup): ResponseEntity<*> {
        val group = Crud.find { groupRepository.findById(jlGroup.groupId) }
        val user = Crud.find { userRepository.findById(jlGroup.userId) }
        user.joinGroup(group)
        userRepository.save(user)
        return ResponseEntity.status(202).body(null)
    }

    @PostMapping("/create-group")
    fun newGroup(@Valid @RequestBody tgroup: TGroup): ResponseEntity<Group> {
        val user = Crud.find { userRepository.findById(tgroup.userId) }
        val group = groupRepository.save(Group(name = tgroup.name, description = tgroup.description))
        user.administerGroup(group)
        userRepository.save(user)
        return ResponseEntity.status(201).body(group)
    }

    @PostMapping("/leave-group")
    fun leaveGroup(@Valid @RequestBody leaveGroupPayload: JLGroup): ResponseEntity<*> {
        val group = Crud.find { groupRepository.findById(leaveGroupPayload.groupId) }
        val user = Crud.find { userRepository.findById(leaveGroupPayload.userId) }
        user.leaveGroup(group)
        group.removeUser(user)
        userRepository.save(user)
        groupRepository.save(group)
        return ResponseEntity.status(204).body(null)
    }

    @GetMapping("/groups/{id}")
    fun getGroup(@PathVariable id: String): ResponseEntity<Group> = ResponseEntity.ok(Crud.find { groupRepository.findById(id) })

    @GetMapping("/groups")
    fun getGroups():ResponseEntity<List<Group>> = ResponseEntity.ok(groupRepository.findAll())

    @GetMapping("/users")
    fun getUsers(): ResponseEntity<List<User>> = ResponseEntity.ok(userRepository.findAll())
}