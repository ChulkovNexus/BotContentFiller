package com.bottools.botcontentfiller.model

import kotlin.properties.ObservableProperty
import kotlin.reflect.KProperty

class ObservableString(var string: String) : ObservableProperty<String>(string) {

    override fun afterChange(property: KProperty<*>, oldValue: String, newValue: String) {
        super.afterChange(property, oldValue, newValue)
        string = newValue
    }
}
