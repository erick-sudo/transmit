package com.whisper.transmit.controllers

import com.whisper.transmit.models.TDestination
import com.whisper.transmit.models.TMessage
import org.springframework.messaging.handler.annotation.MessageMapping
import org.springframework.messaging.handler.annotation.SendTo
import org.springframework.web.bind.annotation.RestController

@RestController
class ChatController {

    @MessageMapping("/incoming")
    fun inboundBroadcast(tMessage: TMessage) {
        println("------------------------------------------------------------------------")
        println("From: ${tMessage.getSource()}")
        println("To: ${tMessage.getDestination().map { "(${it.name} ${it.identifier})" }}")
        println("Message: ${tMessage.getText()}")
        println("------------------------------------------------------------------------")
    }

    @SendTo("/greeting")
    fun outboundBroadcast(): TMessage {
        return TMessage(
            text = "Transmit; Outbound message.",
            source = "0700000000",
            destinations = listOf(
                TDestination("Erick Ochieng", "erick"),
                TDestination("John Doe", "john"),
                TDestination("Sharon Meli", "sharon"),
                TDestination("Lilian Njoki", "lilian")
            )
        )
    }
}