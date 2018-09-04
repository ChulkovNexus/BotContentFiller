package com.bottools.botcontentfiller.model

open class MapTile : Tile {

    constructor(posX: Int, posY: Int) {
        this.posX = posX
        this.posY = posY
    }

    var posX = 0
    var posY = 0
    var biomeId = 0
    var buildings = ArrayList<Item>()
    var events = ArrayList<Long>()

}