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

    private fun getSideText(tile: MapTile, range: Int, function: (Int) -> MapTile?) : CharSequence {
        var text = " "
        val nextCell = function.invoke(range)
        if (nextCell == null) {
            text += getUnpassableDefaults(tile)
        } else if (nextCell.isUnpassable == true) {
            text += getUnpassableDefaults(nextCell)
            if (seeThrowCondition(nextCell) && visionRange > range) {
                text += " " + getSideText(tile, range+1, function)
            }
        } else {
            text += getRangeDependedText(nextCell, range) ?: " -not filled- "
            if (seeThrowCondition(nextCell) && visionRange > range) {
                text += " " + map.defaultBehindsTexts.getRandItem() + " " + getSideText(tile, range+1, function)
            }
        }
        return text
    }

    override fun getRangeDependedText(nextCell: MapTile, range: Int): String? {
        if (range == 1) {
            return nextCell.nextTileCustomDescription
        }   else {
            return nextCell.customFarBehindText
        }
    }

    protected open fun seeThrowCondition(nextCell: MapTile) = nextCell.canSeeThrow != false

    override fun getTopText(tile: MapTile): String {
        var text = map.defaultTopMovingTexts.getRandItem() ?: ""
        text+= getSideText(tile, 1, { range -> map.getTopCell(tile.posX, tile.posY, range) })
        return text
    }

    override fun getRightText(tile: MapTile): String {
        var text = map.defaultRightMovingTexts.getRandItem() ?: ""
        text += getSideText(tile, 1, { range -> map.getRightCell(tile.posX, tile.posY, range) })
        return text
    }

    override fun getLeftText(tile: MapTile): String {
        var text = map.defaultLeftMovingTexts.getRandItem() ?: ""
        text += getSideText(tile, 1, { range -> map.getLeftCell(tile.posX, tile.posY, range) })
        return text
    }

    override fun getBottomText(tile: MapTile): String {
        var text = map.defaultBottomMovingTexts.getRandItem() ?: ""
        text += getSideText(tile, 1, { range -> map.getBottomCell(tile.posX, tile.posY, range) })
        return text
    }

    override fun getCurrentTileText(tile: MapTile): String {
        return tile.thisTileCustomDescription ?: " -not filled- "
    }

}