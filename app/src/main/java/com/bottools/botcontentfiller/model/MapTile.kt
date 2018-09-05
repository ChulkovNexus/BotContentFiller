package com.bottools.botcontentfiller.model

import io.realm.RealmList
import io.realm.RealmObject
import io.realm.annotations.PrimaryKey
import java.io.Serializable

open class MapTile : RealmObject, Serializable {


    @PrimaryKey
    var id = 0

    constructor()
    constructor(posX: Int, posY: Int) : super() {
        this.posX = posX
        this.posY = posY
    }

    var posX = 0
    var posY = 0
    var biomeId = 0
    var buildings = RealmList<Item>()
    var events = RealmList<Long>()

    var thisTileCustomDescription : String? = null
    var canSeeThrow: Boolean? = null
    var isUnpassable : Boolean? = false
    var nextTileCustomDescription : String? = null
    var customFarBehindText : String? = null
}