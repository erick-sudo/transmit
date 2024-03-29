package com.whisper.transmit.models

import com.fasterxml.jackson.annotation.JsonIgnore
import jakarta.persistence.*
import jakarta.validation.constraints.NotBlank
import java.util.UUID

@Entity
@Table(name = "users")
class User(
        @Id var id: String = UUID.randomUUID().toString(),
        @Column(nullable = false) @NotBlank var username: String,
        @Column(nullable = false) @NotBlank var name: String,
        @Column(nullable = false) @NotBlank var phone: String
) {
    @JsonIgnore
    @ManyToMany(fetch = FetchType.EAGER)
    @JoinTable(
            name = "administration_groups",
            joinColumns = [JoinColumn(name = "user_id")],
            inverseJoinColumns = [JoinColumn(name = "group_id")]
    )
    var administrationGroups: MutableList<Group> = mutableListOf()

    @JsonIgnore
    @ManyToMany(fetch = FetchType.EAGER)
    @JoinTable(
            name = "user_groups",
            joinColumns = [JoinColumn(name = "user_id")],
            inverseJoinColumns = [JoinColumn(name = "group_id")]
    )
    var groups: MutableList<Group> = mutableListOf()

    fun administerGroup(group: Group) {
        administrationGroups.add(group)
    }

    fun joinGroup(group: Group) {
        groups.add(group)
    }

    fun leaveGroup(group: Group) {
        groups.remove(group)
    }
}