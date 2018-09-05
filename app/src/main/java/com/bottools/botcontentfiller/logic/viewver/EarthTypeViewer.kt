package com.bottools.botcontentfiller.logic.viewver

import com.bottools.botcontentfiller.manager.DatabaseManager
import com.bottools.botcontentfiller.model.Biome
import com.bottools.botcontentfiller.model.MapTile
import com.bottools.botcontentfiller.model.WorldMap
import com.bottools.botcontentfiller.utils.getRandItem
import java.util.ArrayList

open class EarthTypeViewer(map: WorldMap) : Viewer(map) {
    var visionRange = 2

    var biomes = ArrayList<Biome>()

    init {
        biomes = DatabaseManager.getBiomes()
    }

    fun getUnpassableDefaults(tile: MapTile) : String {
        val biome = biomes.firstOrNull { tile.biomeId == it.id }
        return biome?.unpassabledefaults?.getRandItem() ?: ""
    }

    private fun getSideText(tile: MapTile, count: Int, function: () -> MapTile?) : CharSequence {
        var text = ""
        text += map.defaultTopMovingTexts
        val topCell = function.invoke()
        if (topCell == null) {
            text += getUnpassableDefaults(tile)
        } else if (topCell.isUnpassable == true) {
            text += getUnpassableDefaults(topCell)
            if (seeThrowCondition(topCell) && visionRange > count) {
                getTopText(tile, count+1)
            }
        } else {
            text += topCell.nextTileCustomDescription ?: " -not filled- "
            if (seeThrowCondition(topCell) && visionRange > count) {
                getTopText(tile, count+1)
            }
        }
        return text
    }

    protected open fun seeThrowCondition(topCell: MapTile) = topCell.canSeeThrow != false

    override fun getTopText(tile: MapTile, count: Int): CharSequence {
        return getSideText(tile, count, { map.getTopCell(tile.posX, tile.posY, count) })
    }

    override fun getRightText(tile: MapTile, count: Int): CharSequence {
        return getSideText(tile, count, { map.getRightCell(tile.posX, tile.posY, count) })
    }

    override fun getLeftText(tile: MapTile, count: Int): CharSequence {
        return getSideText(tile, count, { map.getLeftCell(tile.posX, tile.posY, count) })
    }

    override fun getBottomText(tile: MapTile, count: Int): CharSequence {
        return getSideText(tile, count, { map.getBottomCell(tile.posX, tile.posY, count) })
    }

    override fun getCurrentTileText(tile: MapTile): CharSequence {
        return tile.thisTileCustomDescription ?: " -not filled- "
    }

}