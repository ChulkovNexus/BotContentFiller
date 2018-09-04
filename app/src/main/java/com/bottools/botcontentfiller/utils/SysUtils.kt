package com.bottools.botcontentfiller.utils

import java.util.*


fun <T> ArrayList<T>.getRandItem() : T? {
    if (isEmpty()) {
        return null
    }
    return shuffled().take(1)[0]
}

fun ClosedRange<Int>.random() = Random().nextInt((endInclusive + 1) - start) +  start