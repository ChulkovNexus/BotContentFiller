package com.bottools.botcontentfiller.ui.views.layoutchildren

import android.content.Context
import android.util.AttributeSet
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.AdapterView
import android.widget.ArrayAdapter
import android.widget.Spinner
import android.widget.TextView
import com.bottools.botcontentfiller.R

class StringListSelectorView @JvmOverloads constructor (var list: ArrayList<String>, var selectedPosition: Int?, var descr: String, context: Context, attrs: AttributeSet? = null) : AbstractChild(context, attrs) {

    init {
        if (selectedPosition == null) {
            selectedPosition = 0
        }

        val view = LayoutInflater.from(context).inflate(R.layout.layout_color_filler, this, false)
        val descriptionTv = view.findViewById<TextView>(R.id.description)
        val innerContainer = view.findViewById<Spinner>(R.id.color_selector)
        descriptionTv.text = descr
        val colorsAdapter = StringsAdapter(context)
        colorsAdapter.addAll(list)
        innerContainer.adapter = colorsAdapter

        innerContainer.setSelection(selectedPosition!!)
        innerContainer.onItemSelectedListener = object : AdapterView.OnItemSelectedListener {
            override fun onNothingSelected(parent: AdapterView<*>?) {
            }

            override fun onItemSelected(parent: AdapterView<*>?, view: View?, position: Int, id: Long) {
                selectedPosition = position
                changeListener?.onChange()
            }
        }
        this.addView(view)
    }

    class StringsAdapter(context: Context) : ArrayAdapter<String>(context, android.R.layout.simple_spinner_item) {

        override fun getDropDownView(position: Int, convertView: View?, parent: ViewGroup?): View {
            val view =  super.getDropDownView(position, convertView, parent)
            (view as TextView).text = getItem(position)
            return view
        }

        override fun getView(position: Int, convertView: View?, parent: ViewGroup?): View {
            val view =  super.getView(position, convertView, parent)
            (view as TextView).text = getItem(position)
            return view
        }
    }
}