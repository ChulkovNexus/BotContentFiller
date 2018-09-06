package com.bottools.botcontentfiller.model

import io.realm.RealmObject
import java.util.*

open class Event : RealmObject() {

    fun initEventId() {
        eventId = Random().nextLong()
    }

    var eventText = String()
    var probability = 1f
    var eventId : Long = 0L
    var isGlobal = false
}