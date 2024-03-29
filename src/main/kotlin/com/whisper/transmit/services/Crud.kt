package com.whisper.transmit.services

import com.whisper.transmit.exceptions.TException
import java.util.*

object Crud {
    inline fun <reified T> find(entityFinder: () -> Optional<T>): T {

        val optionalEntity =  entityFinder()

        if (optionalEntity.isPresent) {
            return optionalEntity.get()
        }

        throw TException(404, "${T::class.java.simpleName} not found.")
    }
}