package com.bottools.botcontentfiller.model

import io.realm.RealmObject
import io.realm.annotations.PrimaryKey

open class MapResource : RealmObject(){

    @PrimaryKey
    var id = 0
    var name = ""
    var description = ""
}
