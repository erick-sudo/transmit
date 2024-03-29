package com.whisper.transmit.models

import com.fasterxml.jackson.annotation.JsonIgnore
import jakarta.persistence.*
import java.util.*

@Entity
@Table(name = "groups")
class Group(
        @Id var id: String = UUID.randomUUID().toString(),
        @Column(unique = true) var name: String,
        var description: String
) {

    @JsonIgnore
    @ManyToMany(mappedBy = "administrationGroups")
    var administrators = mutableListOf<User>()

    @JsonIgnore
    @ManyToMany(mappedBy = "groups")
    var users = mutableListOf<User>()

    fun removeUser(user: User) {
        users.remove(user)
    }
}