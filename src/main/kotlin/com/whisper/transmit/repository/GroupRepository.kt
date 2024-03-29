package com.whisper.transmit.repository

import com.whisper.transmit.models.Group
import org.springframework.data.jpa.repository.JpaRepository

interface GroupRepository: JpaRepository<Group, String>