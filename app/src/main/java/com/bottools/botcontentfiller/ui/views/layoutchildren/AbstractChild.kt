package com.bottools.botcontentfiller.ui.views.layoutchildren

import android.content.Context
import android.util.AttributeSet
import android.view.View
import android.view.ViewGroup
import android.widget.LinearLayout

abstract class AbstractChild  @JvmOverloads constructor(context: Context, attrs: AttributeSet? = null) : LinearLayout(context, attrs) {

    var changeListener : ChangeListener? = null
    fun attachToView(container: ViewGroup) {
        container.addView(this)
    }

    interface ChangeListener {
        fun onChange()
    }
}