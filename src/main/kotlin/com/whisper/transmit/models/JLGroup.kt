package com.whisper.transmit.models

import com.fasterxml.jackson.annotation.JsonProperty

data class JLGroup(
        @JsonProperty("group_id") var groupId: String,
        @JsonProperty("user_id") var userId: String
)