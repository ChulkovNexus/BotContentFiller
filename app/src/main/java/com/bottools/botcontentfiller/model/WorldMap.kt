package com.bottools.botcontentfiller.model

import io.realm.RealmList
import io.realm.RealmObject
import io.realm.RealmResults
import io.realm.annotations.Ignore
import io.realm.annotations.PrimaryKey

open class WorldMap : RealmObject() {

    fun initMap(xSize: Int, ySize: Int) {
        for (i in 0 until ySize) {
            tiles.add(ArrayList())
            for (j in 0 until xSize) {
                tiles[i].add(MapTile(j, i))
            }
        }
    }

    fun fillFromBase(listFromBase : List<MapTile>) {
        val maxPosX = listFromBase.maxBy { it.posX }?.posX ?: 0
        val maxPosY = listFromBase.maxBy { it.posY }?.posY ?: 0
        initMap(maxPosX + 1, maxPosY + 1)
        listFromBase.forEach {
            tiles[it.posY][it.posX] = it
        }
    }

    @PrimaryKey
    var id = 0x0001 // single instance in base
    var defaultLeftMovingTexts = String()
    var defaultRightMovingTexts = String()
    var defaultLookingForWayPrefix = String()
    var defaultTopMovingTexts = String()
    var defaultBottomMovingTexts = String()
    var defaultBehindsTexts = String()
    var defaultUnpassableText = String()

    @Ignore
    var tiles = ArrayList<ArrayList<MapTile>>()

    //TODO move to database
    var events = RealmList<Event>()

    fun getTile(currX: Int, currY: Int) : MapTile {
        return tiles[currY][currX]
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