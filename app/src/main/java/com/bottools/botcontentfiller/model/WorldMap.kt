package com.bottools.botcontentfiller.model

import java.util.*

class WorldMap {

    fun initMap(xSize: Int, ySize: Int) {
        for (i in 0 until ySize) {
            tiles.add(ArrayList())
            for (j in 0 until xSize) {
                tiles[i].add(MapTile(j, i))
            }
        }
    }
    val defaultLeftMovingTexts = ArrayList<String>()
    val defaultRightMovingTexts = ArrayList<String>()
    var defaultLookingForWayPrefix = ArrayList<String>()
    val defaultTopMovingTexts = ArrayList<String>()
    val defaultBottomMovingTexts = ArrayList<String>()
    val unpassableDefaults = ArrayList<String>()
    val unknownsDefaults = ArrayList<String>()
    val defaultBehindsTexts = ArrayList<String>()

    val tiles = ArrayList<ArrayList<MapTile>>()
    var events = ArrayList<Event>()

    class Event {
        constructor() {
            eventId = Random().nextLong()
        }

        var eventText = String()
        var probability = 1f
        var eventId : Long = 0L
        var isGlobal = false
    }
}