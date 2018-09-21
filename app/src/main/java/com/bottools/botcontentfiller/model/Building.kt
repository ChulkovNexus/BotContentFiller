package com.bottools.botcontentfiller.model

import io.realm.RealmList
import io.realm.RealmObject
import io.realm.annotations.PrimaryKey

open class Building : RealmObject() {

    @PrimaryKey
    var id = 0
    
    var name = ""
    var description = ""
    var requiredBuildingsIds = RealmList<Int>()
    var energyOutput = 0
    var energyRequered = 0
    var temperatureOtput = 0


    override fun equals(other: Any?): Boolean {
        if (other !is Building) {
            return false
        } else {
            return id == other.id
        }
    }
}