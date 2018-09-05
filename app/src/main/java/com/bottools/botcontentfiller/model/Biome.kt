package com.bottools.botcontentfiller.model

import io.realm.RealmList
import io.realm.RealmObject
import io.realm.annotations.PrimaryKey
import java.io.Serializable
import java.util.ArrayList

open class Biome : RealmObject(), Serializable {

    @PrimaryKey
    var id = 0
    var name = String()
    var color = 0
    var tiles = RealmList<BiomeTile>()
    var unpassabledefaults = RealmList<String>()
}