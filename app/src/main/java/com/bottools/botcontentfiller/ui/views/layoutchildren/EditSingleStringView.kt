package com.bottools.botcontentfiller.ui.views.layoutchildren

import android.content.Context
import android.text.Editable
import android.text.TextWatcher
import android.util.AttributeSet
import android.view.LayoutInflater
import android.widget.EditText
import android.widget.TextView
import com.bottools.botcontentfiller.R

class EditSingleStringView @JvmOverloads constructor (var str : String?, var descr: String?, context: Context, attrs: AttributeSet? = null) : AbstractChild(context, attrs) {

    init {
        if (str==null) str = ""
        val view = LayoutInflater.from(context).inflate(R.layout.layout_string_filler, this, false)
        val descriptionTv = view.findViewById<TextView>(R.id.description)
        val editText = view.findViewById<EditText>(R.id.value)
        descriptionTv.text = descr
        editText.setText(str)
        editText.addTextChangedListener(object : TextWatcher {
            override fun afterTextChanged(s: Editable?) {
                str = s.toString()
            }

            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {
            }

            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
            }
        })
        addView(view)
    }

}