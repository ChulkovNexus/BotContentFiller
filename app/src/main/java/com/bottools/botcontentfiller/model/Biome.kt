package com.bottools.botcontentfiller.model

import io.realm.RealmObject
import io.realm.annotations.PrimaryKey
import java.io.Serializable
import java.util.ArrayList

class Biome : RealmObject(), Serializable {

    @PrimaryKey
    val id = 0
    val name = String()
    val tiles = ArrayList<BiomeTile>()
    val unpassabledefaults = ArrayList<String>()

}