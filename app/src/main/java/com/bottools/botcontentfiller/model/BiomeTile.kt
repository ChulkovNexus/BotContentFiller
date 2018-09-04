package com.bottools.botcontentfiller.model

class BiomeTile: Tile() {

    val probability = 0f
    val possibleEvents = ArrayList<Long>()
    var possibleBuildings = ArrayList<Item>()
}