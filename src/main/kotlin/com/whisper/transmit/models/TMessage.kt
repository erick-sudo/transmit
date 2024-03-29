package com.whisper.transmit.models

import com.whisper.transmit.models.TDestination

class TMessage(
        private var text: String,
        private var source: String,
        private var destinations: List<TDestination>
) {
    val getText = { this.text }
    val getSource = { this.source }
    val getDestination = { this.destinations }
}