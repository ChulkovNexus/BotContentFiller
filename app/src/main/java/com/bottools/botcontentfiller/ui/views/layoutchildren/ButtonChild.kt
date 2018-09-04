package com.bottools.botcontentfiller.ui.views.layoutchildren

import android.content.Context
import android.util.AttributeSet
import android.widget.Button


class ButtonChild @JvmOverloads constructor (var text : String?, var listener: ()->Unit, context: Context, attrs: AttributeSet? = null) : AbstractChild(context, attrs) {

    init {
        val view = Button(context)
        view.text = text
        view.setOnClickListener {
            listener.invoke()
        }
        addView(view)
    }

}