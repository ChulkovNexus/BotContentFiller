package com.bottools.botcontentfiller.model

import io.realm.RealmList
import io.realm.RealmObject
import io.realm.annotations.PrimaryKey
import java.io.Serializable

open class Item : RealmObject(), Serializable {

    @PrimaryKey
    var id = 0
    var name: String? = null
    var descr: String? = null
    var weight = 0
    var craftOn : Item? = null
    var creftFrom: RealmList<Item>? = null
    var isBuilding = false
    var isWerable = false

}