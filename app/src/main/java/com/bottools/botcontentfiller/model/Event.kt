package com.bottools.botcontentfiller.model

import io.realm.RealmList
import io.realm.RealmObject
import java.util.*

open class Event : RealmObject() {

    fun initEventId() {
        eventId = Random().nextLong()
    }

    var eventId : Long = 0L
    var eventText = RealmList<String>()
    var probability = 1f
    var probabilityFromAttentionFactor = 0f
    var probabilityFromStealthFactor = 0f
    var isGlobal = false
}