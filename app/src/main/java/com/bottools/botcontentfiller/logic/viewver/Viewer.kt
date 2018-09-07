package com.bottools.botcontentfiller.logic.viewver

import com.bottools.botcontentfiller.model.MapTile
import com.bottools.botcontentfiller.model.WorldMap

abstract class Viewer(val map: WorldMap) {
    abstract fun getTopText(tile: MapTile): CharSequence
    abstract fun getLeftText(tile: MapTile): CharSequence
    abstract fun getRightText(tile: MapTile): CharSequence
    abstract fun getBottomText(tile: MapTile): CharSequence
    abstract fun getCurrentTileText(tile: MapTile): CharSequence
    abstract fun getRangeDependedText(nextCell: MapTile, range: Int): String?
}