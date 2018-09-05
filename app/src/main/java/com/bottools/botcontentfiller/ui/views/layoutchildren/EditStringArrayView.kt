package com.bottools.botcontentfiller.ui.views.layoutchildren

import android.content.ClipDescription
import android.content.Context
import android.text.Editable
import android.text.TextWatcher
import android.util.AttributeSet
import android.view.LayoutInflater
import android.view.View
import android.widget.Button
import android.widget.EditText
import android.widget.LinearLayout
import android.widget.TextView
import com.bottools.botcontentfiller.R
import io.realm.RealmList

class EditStringArrayView  : AbstractChild{

//    @JvmOverloads
//    constructor (strArray : ArrayList<String>, description: String, context: Context, attrs: AttributeSet? = null): super(context, attrs){
//        fillLayout(this, strArray, description)
//    }

    @JvmOverloads
    constructor (strArray : RealmList<String>, description: String, context: Context, attrs: AttributeSet? = null): super(context, attrs){
        fillLayout(this, strArray, description)
    }

    fun fillLayout(container: LinearLayout, list: RealmList<String>, description: String) {
        val view = LayoutInflater.from(context).inflate(R.layout.layout_list_filler, container, false)
        val listDescription = view.findViewById<TextView>(R.id.list_discription)
        val innerContainer = view.findViewById<LinearLayout>(R.id.container)
        val addButton = view.findViewById<Button>(R.id.add_button)
        view.findViewById<Button>(R.id.load_from_list).visibility = View.GONE
        listDescription.text = description
        list.forEach {
            addDefaultEditable(innerContainer, list, it)
        }
        addButton.setOnClickListener {
            addDefaultEditable(innerContainer, list)
        }
        container.addView(view)
    }

    private fun addDefaultEditable(container: LinearLayout, list: RealmList<String>, default: String? = null) {
        val view = LayoutInflater.from(context).inflate(R.layout.layout_default_editable, container, false)

        val editText = view.findViewById<EditText>(R.id.edit_text)
        if(default==null)
            list.add(String())
        else
            editText.setText(default)
        editText.addTextChangedListener(object : TextWatcher {
            override fun afterTextChanged(s: Editable) {
                val indexOfChild = container.indexOfChild(view)
                if (indexOfChild!= -1) {
                    list[indexOfChild] = s.toString()
                }
            }

            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {}
            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {}
        })

        val removeButton = view.findViewById<Button>(R.id.remove)
        removeButton.setOnClickListener {
            val indexOfChild = container.indexOfChild(view)
            if (indexOfChild!= -1) {
                list.removeAt(indexOfChild)
            }
            container.removeView(view)
        }

        container.addView(view)
    }
}