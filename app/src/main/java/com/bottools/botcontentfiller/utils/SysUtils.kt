package com.bottools.botcontentfiller.utils


fun <T> ArrayList<T>.getRandItem() : T? {
    if (isEmpty()) {
        return null
    }
    return shuffled().take(1)[0]
}