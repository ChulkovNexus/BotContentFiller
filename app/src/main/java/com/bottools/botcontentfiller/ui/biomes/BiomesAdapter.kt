package com.bottools.botcontentfiller.ui.biomes

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import android.widget.ImageButton
import android.widget.TextView
import com.bottools.botcontentfiller.R
import com.bottools.botcontentfiller.model.Biome

class BiomesAdapter(context: Context, val editBiomeListener : (Biome)-> Unit ) : ArrayAdapter<Biome>(context, R.layout.adapter_layout_biome) {

    val inflater = LayoutInflater.from(context)
    override fun getView(position: Int, convertView: View?, parent: ViewGroup?): View {
        val holder: ViewHolder
        val view : View
        if (convertView == null) {
            view = inflater.inflate(R.layout.adapter_layout_biome, parent, false)
            holder = ViewHolder(view)
            view.tag = holder
        } else {
            view = convertView
            holder = view.tag as ViewHolder
        }
        holder.bind(getItem(position))
        return view
    }

    inner class ViewHolder(val view: View) {
        val editButton = view.findViewById<ImageButton>(R.id.edit_button)
        val descr = view.findViewById<TextView>(R.id.description)
        fun bind(item: Biome) {
            descr.text = item.name
            editButton.setOnClickListener({
                editBiomeListener.invoke(item)
            })
        }
    }
}