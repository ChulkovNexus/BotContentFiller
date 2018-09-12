package com.bottools.botcontentfiller.model

import com.bottools.botcontentfiller.utils.getRandItem
import io.realm.RealmList
import io.realm.RealmObject
import io.realm.annotations.PrimaryKey
import java.io.Serializable
import java.util.*

open class MapTile : RealmObject, Serializable {

    @PrimaryKey
    var id = 0

    constructor()
    constructor(posX: Int, posY: Int) : super() {
        this.posX = posX
        this.posY = posY
        id = "${posX + 1000}${posY + 1000}".toInt()
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

    fun fillFromBiome(selectedBiome: Biome) {
        biomeId = selectedBiome.id
        val percentage = Random().nextInt(100)
        val randTile = getRandomTile(selectedBiome.tiles, percentage)
        randTile?.let {
            canSeeThrow = randTile.canSeeThrow
            isUnpassable = randTile.isUnpassable
            customFarBehindText = randTile.customFarBehindText
            thisTileCustomDescription = randTile.thisTileCustomDescription
            nextTileCustomDescription  = randTile.nextTileCustomDescription
        }
    }

    private fun getRandomTile(tiles: RealmList<BiomeTile>, percentage: Int): BiomeTile? {
        val randItem = tiles.getRandItem()
        val probability = randItem?.probability ?: 0f
        if ((probability * 100f).toInt() < percentage ) {
            return randItem
        } else {
            return getRandomTile(tiles, percentage)
        }
    }
}