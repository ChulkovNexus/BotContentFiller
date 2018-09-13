package com.bottools.botcontentfiller.model

import io.realm.RealmList
import io.realm.RealmObject
import io.realm.annotations.PrimaryKey

class WorkType : RealmObject() {

    @PrimaryKey
    var id = 0

    var description = ""
    var baseWorkTime = 0
    var workTypeGroup : WorkTypeGroup ? = null
    var requiredItems = RealmList<Item>()
    var requiredBuildings = RealmList<Building>()
    // output can be buildings, expirience or items
    // need skill levels limits
    var stealthFactor = 0f
}