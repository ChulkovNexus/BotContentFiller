package com.bottools.botcontentfiller.ui.views.layoutchildren

import android.content.Context
import android.text.Editable
import android.text.InputType
import android.text.TextWatcher
import android.util.AttributeSet
import android.view.LayoutInflater
import android.widget.EditText
import android.widget.TextView
import com.bottools.botcontentfiller.R

class EditPercentView @JvmOverloads constructor (var percent: Float?, var descr: String, context: Context, attrs: AttributeSet? = null) : AbstractChild(context, attrs) {

    init {
        if (percent == null) {
            percent = 0f
        }

        val view = LayoutInflater.from(context).inflate(R.layout.layout_string_filler, this, false)
        val descriptionTv = view.findViewById<TextView>(R.id.description)
        val innerContainer = view.findViewById<EditText>(R.id.value)
        innerContainer.inputType = InputType.TYPE_CLASS_NUMBER or InputType.TYPE_NUMBER_FLAG_DECIMAL
        descriptionTv.text = descr
        innerContainer.setText("${(percent!! * 100).toInt()}")
        innerContainer.addTextChangedListener(object : TextWatcher {
            override fun afterTextChanged(s: Editable?) {
                try {
                    percent = s.toString().toInt() / 100f
                } catch (e: NumberFormatException) {
                    e.printStackTrace()
                }
            }

            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {
            }

            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
            }
        })
        this.addView(view)
    }
}