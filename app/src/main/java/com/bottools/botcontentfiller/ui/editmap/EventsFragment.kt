package com.bottools.botcontentfiller.ui.editmap

import android.content.Context
import android.os.Bundle
import android.support.v4.app.ListFragment
import android.view.View
import android.widget.ArrayAdapter
import android.widget.ListView
import com.bottools.botcontentfiller.model.Event


class EventsFragment : ListFragment() {

    var map: com.bottools.botcontentfiller.model.WorldMap? = null
    var listener: EventChoosedListener ? = null

    override fun onAttach(context: Context?) {
        super.onAttach(context)
        val ac = activity as ActivityEditMap
        map = ac.map
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)

        val adapter = ArrayAdapter(activity, android.R.layout.simple_list_item_1, map!!.events.map { it.eventText })
        listAdapter = adapter
    }

    override fun onListItemClick(l: ListView?, v: View?, position: Int, id: Long) {
        super.onListItemClick(l, v, position, id)
        val event = map!!.events[position]
        event?.let {
            listener?.eventChoosed(it)
        }
    }

    interface EventChoosedListener {
        fun eventChoosed(event: Event)
    }
}