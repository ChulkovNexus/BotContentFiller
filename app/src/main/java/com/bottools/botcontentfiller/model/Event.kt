package com.bottools.botcontentfiller.model

import io.realm.RealmList
import io.realm.RealmObject
import io.realm.annotations.PrimaryKey
import java.util.*

open class Event : RealmObject() {

    fun initEventId() {
        id = Random().nextInt()
    }

    @PrimaryKey
    var id = 0
    var eventText = RealmList<String>()
    var probability = 1f
    var probabilityFromAttentionFactor = 0f
    var probabilityFromStealthFactor = 0f
    var isGlobal = false


    override fun equals(other: Any?): Boolean {
        if (other !is Building) {
            return false
        } else {
            return id == other.id
        }
    }
}