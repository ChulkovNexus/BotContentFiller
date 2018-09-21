package com.bottools.botcontentfiller.model

import android.text.TextUtils
import io.realm.RealmList
import io.realm.RealmObject
import io.realm.annotations.PrimaryKey

open class WorkType : RealmObject() {

    @PrimaryKey
    var id = 0

    var description = ""
    var baseWorkTime = 0
    var workTypeGroup = ""
    var requiredItems = RealmList<Item>()
    var requiredBuildings = RealmList<Building>()
    // output can be buildings, expirience or items
    // need skill levels limits
    var stealthFactor = 0f


    fun setWorkTypeGroup(enum: WorkTypeGroup) {
        this.workTypeGroup = enum.toString()
    }

    fun getWorkTypeGroup(): WorkTypeGroup {
        if (TextUtils.isEmpty(workTypeGroup)) {
            return WorkTypeGroup.Default
        } else {
            return WorkTypeGroup.valueOf(workTypeGroup)
        }
    }
}