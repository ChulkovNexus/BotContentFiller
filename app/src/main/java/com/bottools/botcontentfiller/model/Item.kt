package com.bottools.botcontentfiller.model

import android.text.TextUtils
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
    var itemGroup = ""
    var wearType = ""
    var bodyPartCoverage = ""
    var accuracyRangeFactor = 0f
    var rangeDamageFactor = 0f
    var armor = 0f
    var maxHealth = 0 // percent


    fun setBodyPartCoverage(enumList: ArrayList<BodyParts>) {
        var result = ""
        enumList.forEach {
            result += " $it"
        }
        this.bodyPartCoverage = result
    }

    fun getBodyPartCoverage(): ArrayList<BodyParts>{
        val result = ArrayList<BodyParts>()
        bodyPartCoverage.split(" ").forEach {
            if (!TextUtils.isEmpty(it)) {
                result.add(BodyParts.valueOf(it))
            }
        }
        return result
    }

    fun setItemGroup(enum: ItemGroup) {
        this.itemGroup = enum.toString()
    }

    fun getItemGroup(): ItemGroup {
        if (itemGroup.isEmpty()) {
            return ItemGroup.Default
        } else {
            return ItemGroup.valueOf(itemGroup)
        }
    }

    fun setWearType(enum: WearType) {
        this.wearType = enum.toString()
    }

    fun getWearType(): WearType {
        if (TextUtils.isEmpty(wearType)) {
            return WearType.Default
        } else {
            return WearType.valueOf(wearType)
        }
    }
}