package com.bottools.botcontentfiller.model

import io.realm.RealmList
import io.realm.RealmObject
import io.realm.annotations.PrimaryKey
import java.io.Serializable

open class BiomeTile: RealmObject(), Serializable {

    @PrimaryKey
    var id = 0

    var probability : Float? = 0f
    var possibleEvents = RealmList<Long>()
    var possibleBuildings = RealmList<Item>()
    var thisTileCustomDescription : String? = null
    var canSeeThrow: Boolean? = null
    var isUnpassable : Boolean? = false
    var nextTileCustomDescription : String? = null
    var customFarBehindText : String? = null
}