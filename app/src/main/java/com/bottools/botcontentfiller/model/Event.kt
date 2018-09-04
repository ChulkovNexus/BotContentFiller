package com.bottools.botcontentfiller.model

import java.util.*

class Event {
    constructor() {
        eventId = Random().nextLong()
    }

    var eventText = String()
    var probability = 1f
    var eventId : Long = 0L
    var isGlobal = false
}