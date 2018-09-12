package com.bottools.botcontentfiller.ui.views.layoutchildren

import android.content.Context
import android.util.AttributeSet
import android.view.LayoutInflater
import android.widget.Switch
import android.widget.TextView
import com.bottools.botcontentfiller.R

class EditBooleanView @JvmOverloads constructor (var boolean: Boolean?, var descr: String, context: Context, default: Boolean = false, attrs: AttributeSet? = null) : AbstractChild(context, attrs) {

    init {
        if (boolean == null) {
            boolean = default
        }

        val view = LayoutInflater.from(context).inflate(R.layout.layout_boolean_filler, this, false)
        val descriptionTv = view.findViewById<TextView>(R.id.description)
        val innerContainer = view.findViewById<Switch>(R.id.boolean_value)
        descriptionTv.text = descr
        innerContainer.isChecked = boolean!!
        innerContainer.setOnCheckedChangeListener { v, isChecked ->
            boolean = isChecked
            changeListener?.onChange()
        }
        this.addView(view)
    }
}