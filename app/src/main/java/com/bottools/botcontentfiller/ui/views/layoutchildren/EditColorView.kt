package com.bottools.botcontentfiller.ui.views.layoutchildren

import android.content.Context
import android.graphics.drawable.ColorDrawable
import android.util.AttributeSet
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.*
import com.bottools.botcontentfiller.R

class EditColorView @JvmOverloads constructor (var color: Int?, var descr: String, context: Context, attrs: AttributeSet? = null) : AbstractChild(context, attrs) {

    init {
        if (color == null) {
            color = 0
        }

        val view = LayoutInflater.from(context).inflate(R.layout.layout_color_filler, this, false)
        val descriptionTv = view.findViewById<TextView>(R.id.description)
        val innerContainer = view.findViewById<Spinner>(R.id.color_selector)
        descriptionTv.text = descr
        val colorsAdapter = ColorsAdapter(context)
        val intArray = context.resources.getIntArray(R.array.colors_arrays).toList()
        colorsAdapter.addAll(intArray)
        innerContainer.adapter = colorsAdapter

        val indexOfFirst = intArray.indexOfFirst { it == color }
        if (indexOfFirst != -1) {
            innerContainer.setSelection(indexOfFirst)
        }
        innerContainer.onItemSelectedListener = object : AdapterView.OnItemSelectedListener {
            override fun onNothingSelected(parent: AdapterView<*>?) {

            }

            override fun onItemSelected(parent: AdapterView<*>?, view: View?, position: Int, id: Long) {
                color = colorsAdapter.getItem(position)
                changeListener?.onChange()
            }
        }
        this.addView(view)
    }

    class ColorsAdapter(context: Context) : ArrayAdapter<Int>(context, android.R.layout.simple_spinner_item) {

        override fun getDropDownView(position: Int, convertView: View?, parent: ViewGroup?): View {
            val view =  super.getDropDownView(position, convertView, parent)
            view.background = ColorDrawable(getItem(position))
            (view as TextView).text = ""
            return view
        }

        override fun getView(position: Int, convertView: View?, parent: ViewGroup?): View {
            val view =  super.getView(position, convertView, parent)
            view.background = ColorDrawable(getItem(position))
            (view as TextView).text = ""
            return view
        }
    }
}