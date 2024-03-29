package com.whisper.transmit.exceptions

class TException(
        var status: Int,
        private var error: String
): Exception() {
    override val message: String
        get() = error
}