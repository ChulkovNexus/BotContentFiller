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
    // output can be buildingsIds, expirience or items
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