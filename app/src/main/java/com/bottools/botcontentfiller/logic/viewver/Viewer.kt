package com.bottools.botcontentfiller.logic.viewver

import com.bottools.botcontentfiller.model.MapTile
import com.bottools.botcontentfiller.model.WorldMap

abstract class Viewer(val map: WorldMap) {
    abstract fun getTopText(tile: MapTile, count: Int = 1): CharSequence
    abstract fun getLeftText(tile: MapTile, count: Int = 1): CharSequence
    abstract fun getRightText(tile: MapTile, count: Int = 1): CharSequence
    abstract fun getBottomText(tile: MapTile, count: Int = 1): CharSequence
    abstract fun getCurrentTileText(tile: MapTile): CharSequence
}