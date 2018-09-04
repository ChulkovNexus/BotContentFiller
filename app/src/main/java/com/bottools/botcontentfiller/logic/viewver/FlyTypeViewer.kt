package com.bottools.botcontentfiller.logic.viewver

import com.bottools.botcontentfiller.model.MapTile
import com.bottools.botcontentfiller.model.WorldMap

class FlyTypeViewer(map: WorldMap) : EarthTypeViewer(map) {

    override fun seeThrowCondition(topCell: MapTile): Boolean {
        return true
    }
}