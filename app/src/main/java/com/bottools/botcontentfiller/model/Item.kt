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
    var itemGroup : ItemGroup? = null
    var accuracy = 0
    var damage = 0
    var accuracyRangeFactor = 0f
    var accuracyRangeDamageFactor = 0f
    var range = 0
    var isMelee = false
    var minDps = 0
    var maxDps = 0
    var bodyPartDurabilityModificator = 0f
    var temperatureModificator = 0
    var bodyPartCoverage = RealmList<BodyParts>()
    var health = 0f // percent
}