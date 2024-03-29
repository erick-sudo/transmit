package com.whisper.transmit.repository

import com.whisper.transmit.models.User
import org.springframework.data.jpa.repository.JpaRepository
import java.util.*

interface UserRepository : JpaRepository<User, String> {
    fun findByNameOrUsernameOrPhone(name: String, username: String, phone: String): Optional<User>
}