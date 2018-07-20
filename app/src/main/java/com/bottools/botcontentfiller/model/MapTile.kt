package com.bottools.botcontentfiller.model

class MapTile {

    constructor(posX: Int, posY: Int) {
        this.posX = posX
        this.posY = posY
    }

    var posX = 0
    var posY = 0
    var isUnpassable = false
    var canSeeThrow: Boolean? = null
    var thisTileCustomDescription = ArrayList<String>()
    var lookingForWayPrefix = ArrayList<String>()
    var nextTileCustomDescription = ArrayList<String>()
    var behindCustomText = ArrayList<String>()
    var customFarBehindText = ArrayList<String>()
    var events = ArrayList<Long>()

}