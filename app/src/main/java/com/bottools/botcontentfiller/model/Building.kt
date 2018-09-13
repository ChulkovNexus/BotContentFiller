package com.bottools.botcontentfiller.model

import io.realm.RealmList
import io.realm.RealmObject
import io.realm.annotations.PrimaryKey

class Building : RealmObject() {

    @PrimaryKey
    var id = 0
    
    var name = ""
    var description = ""
    var requiredBuildings = RealmList<Building>()
    var energyOutput = 0
    var energyRequered = 0
    var temperatureOtput = 0

}