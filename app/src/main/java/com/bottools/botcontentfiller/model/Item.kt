package com.bottools.botcontentfiller.model

import io.realm.RealmList
import io.realm.RealmObject
import io.realm.annotations.PrimaryKey
import java.io.Serializable

open class Item : RealmObject(), Serializable {

    @PrimaryKey
    var id = 0
    var name: String? = null
    var descr: String? = null
    var effects: String? = null
    var weight = 0
    var accuracy = 0
    var range = 0
    var minDps = 0
    var maxDps = 0
    var temperatureModificator = 0
    var itemGroup : ItemGroup? = null
    var wearType : WearType? = null
    var bodyPartCoverage = RealmList<BodyParts>()
    var accuracyRangeFactor = 0f
    var rangeDamageFactor = 0f
    var armor = 0f
    var maxHealth = 0 // percent
}