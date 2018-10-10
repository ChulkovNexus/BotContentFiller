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
    var buildingsIds = RealmList<Int>()
    var eventsIds = RealmList<Int>()
    var availableWorkTypesIds = RealmList<Int>()

    var thisTileCustomDescription : String? = null
    var canSeeThrow: Boolean? = null
    var isUnpassable : Boolean? = false
    var nextTileCustomDescription : String? = null
    var customFarBehindText : String? = null
    var editedAfterBiomeSetted = false
    var moveSpeedFactor: Float? = 0f
    var additionalMoveSpeed: Int? = 0
    var stealthFactor: Float? = 0f
    var additionalStealth: Int? = 0


    fun fillFromBiome(selectedBiome: Biome) {
        biomeId = selectedBiome.id
        val maxBy = selectedBiome.tiles.maxBy { it.probability ?:0f}
        val intPercentage = ((maxBy?.probability ?: 0.01f) * 100f).toInt()
        val percentage = Random().nextInt(intPercentage + 1)
        val randTile = getRandomTile(selectedBiome.tiles, percentage)
        randTile?.let {
            canSeeThrow = randTile.canSeeThrow
            isUnpassable = randTile.isUnpassable
            customFarBehindText = randTile.customFarBehindText
            thisTileCustomDescription = randTile.thisTileCustomDescription
            nextTileCustomDescription  = randTile.nextTileCustomDescription
            availableWorkTypesIds  = randTile.availableWorkTypesIds
            moveSpeedFactor  = randTile.moveSpeedFactor
            additionalMoveSpeed  = randTile.additionalMoveSpeed
            stealthFactor  = randTile.stealthFactor
            additionalStealth  = randTile.additionalStealth
            editedAfterBiomeSetted = false
            buildingsIds = randTile.initialBuildingsIds
            eventsIds = randTile.possibleEventsIds
        }
    }

    private fun getRandomTile(tiles: RealmList<BiomeTile>, percentage: Int): BiomeTile? {
        val randItem = tiles.getRandItem()
        var probability = randItem?.probability ?: 10f
        if (probability == 0f) {
            probability = 100f
        }
        if (percentage <= (probability * 100f).toInt()) {
            return randItem
        } else {
            return getRandomTile(tiles, percentage)
        }
    }
}