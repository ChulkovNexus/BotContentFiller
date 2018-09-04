package com.bottools.botcontentfiller.model

import io.realm.RealmObject
import io.realm.annotations.PrimaryKey
import java.io.Serializable

open class Tile: RealmObject(), Serializable{

    @PrimaryKey
    var thisTileCustomDescription : String? = null
    var canSeeThrow: Boolean? = null
    var isUnpassable = false
    var nextTileCustomDescription : String? = null
    var customFarBehindText : String? = null
}