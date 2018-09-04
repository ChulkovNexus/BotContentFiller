package com.bottools.botcontentfiller.model

import com.bottools.botcontentfiller.utils.getRandItem
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
    val defaultLeftMovingTexts = String()
    val defaultRightMovingTexts = String()
    var defaultLookingForWayPrefix = String()
    val defaultTopMovingTexts = String()
    val defaultBottomMovingTexts = String()
    val defaultBehindsTexts = String()

    val tiles = ArrayList<ArrayList<MapTile>>()

    //TODO move to database
    var events = ArrayList<Event>()
    var biomes = ArrayList<Biome>()

    fun getTile(currX: Int, currY: Int) : MapTile {
        return tiles[currY][currX]
    }

    fun getUnpassableDefaults(tile: MapTile) : String {
        val biome = biomes.firstOrNull { tile.biomeId == it.id }
        return biome?.unpassabledefaults?.getRandItem() ?: ""
    }

    fun getRightCell(currX: Int, currY: Int, count : Int = 1) : MapTile? {
        if (currX + count < tiles[currY].size) {
            return tiles[currY][currX + count]
        } else {
            return null
        }
    }

    fun getLeftCell(currX: Int, currY: Int, count : Int = 1) : MapTile? {
        if (currX - count >= 0) {
            return tiles[currY][currX - count]
        } else {
            return null
        }
    }

    fun getTopCell(currX: Int, currY: Int, count : Int = 1) : MapTile? {
        if (currY - count >= 0) {
            return tiles[currY - count][currX]
        } else {
            return null
        }
    }

    fun getBottomCell(currX: Int, currY: Int, count : Int = 1) : MapTile? {
        if (currY + count < tiles.size) {
            return tiles[currY + count][currX]
        } else {
            return null
        }
    }
}