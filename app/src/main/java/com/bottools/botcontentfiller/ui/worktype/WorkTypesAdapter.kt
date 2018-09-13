package com.bottools.botcontentfiller.ui.worktype

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import android.widget.ImageButton
import android.widget.TextView
import com.bottools.botcontentfiller.R
import com.bottools.botcontentfiller.model.WorkType
import com.bottools.botcontentfiller.utils.getRandItem


class WorkTypesAdapter(context: Context, val editWorkTypeListener : (WorkType)-> Unit, val removeWorkTypeListener : (WorkType)-> Unit ) : ArrayAdapter<WorkType>(context, R.layout.adapter_layout_editing) {

    val inflater = LayoutInflater.from(context)
    override fun getView(position: Int, convertView: View?, parent: ViewGroup?): View {
        val holder: ViewHolder
        val view : View
        if (convertView == null) {
            view = inflater.inflate(R.layout.adapter_layout_editing, parent, false)
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
        val remove = view.findViewById<ImageButton>(R.id.remove_button)
        val descr = view.findViewById<TextView>(R.id.description)

        fun bind(item: WorkType) {
            descr.text = item.description
            editButton.setOnClickListener({
                editWorkTypeListener.invoke(item)
            })
            remove.setOnClickListener({
                removeWorkTypeListener.invoke(item)
            })
        }
    }
}