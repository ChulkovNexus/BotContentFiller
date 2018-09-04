package com.bottools.botcontentfiller.model

import io.realm.RealmObject
import io.realm.annotations.PrimaryKey
import java.io.Serializable
import java.util.ArrayList

class Biome : RealmObject(), Serializable {

    @PrimaryKey
    var id = 0
    var name = String()
    var tiles = ArrayList<BiomeTile>()
    var unpassabledefaults = ArrayList<String>()

}