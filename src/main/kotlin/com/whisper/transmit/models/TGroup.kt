package com.whisper.transmit.models

import com.fasterxml.jackson.annotation.JsonProperty
import jakarta.validation.constraints.NotBlank

data class TGroup(
        @NotBlank var name: String,
        @NotBlank var description: String,
        @NotBlank @JsonProperty("user_id") var userId: String
)