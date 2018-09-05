package com.bottools.botcontentfiller.utils

import io.realm.RealmList
import java.util.*


fun <T> ArrayList<T>.getRandItem() : T? {
    if (isEmpty()) {
        return null
    }
    return shuffled().take(1)[0]
}

fun <T> RealmList<T>.getRandItem() : T? {
    if (isEmpty()) {
        return null
    }
    return shuffled().take(1)[0]
}

fun ClosedRange<Int>.random() = Random().nextInt(endInclusive - start) +  start