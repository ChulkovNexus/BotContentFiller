package com.bottools.botcontentfiller.model

import io.realm.RealmObject
import io.realm.annotations.PrimaryKey

class Building : RealmObject() {

    @PrimaryKey
    var id = 0

    val description = ""
    val requiredBuildings = ArrayList<Building>()
    val energyOutput = 0
    val energyRequered = 0

}