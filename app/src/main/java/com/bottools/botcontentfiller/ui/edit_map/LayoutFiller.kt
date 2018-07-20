package com.bottools.botcontentfiller.ui.edit_map

import android.content.Context
import android.support.v4.app.FragmentManager
import android.text.Editable
import android.text.TextWatcher
import android.view.LayoutInflater
import android.view.View
import android.widget.*
import com.bottools.botcontentfiller.R
import com.bottools.botcontentfiller.model.MapTile
import com.bottools.botcontentfiller.model.WorldMap

class LayoutFiller(val context: Context, val fragmentManager: FragmentManager){

    fun fillLayout(container: LinearLayout, list: ArrayList<String>, description: String) {
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

    private fun addDefaultEditable(container: LinearLayout, list: ArrayList<String>, default: String? = null) {
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

    fun addMapTileEvent(container: LinearLayout, map: WorldMap, mapTile: MapTile, string: String?) {
        val view = LayoutInflater.from(context).inflate(R.layout.layout_list_filler, container, false)
        val listDescription = view.findViewById<TextView>(R.id.list_discription)
        val innerContainer = view.findViewById<LinearLayout>(R.id.container)
        val addButton = view.findViewById<Button>(R.id.add_button)
        val eventsList = ArrayList<WorldMap.Event>()
        listDescription.text = string

        mapTile.events.forEach { eventId ->
            val element = map.events.firstOrNull { it.eventId == eventId }
            if (element!=null)
                eventsList.add(element)
        }
        eventsList.forEach {
            addEvent(innerContainer, map, mapTile, it)
        }
        addButton.setOnClickListener {
            addEvent(innerContainer, map, mapTile)
        }
        val loadFromList = view.findViewById<Button>(R.id.load_from_list)
        loadFromList.setOnClickListener {
            val fragment = EventsFragment()
            fragment.listener = object : EventsFragment.EventChoosedListener {
                override fun eventChoosed(event: WorldMap.Event) {
                    addEvent(container, map, mapTile, event)
                }
            }
            val transaction = fragmentManager.beginTransaction()
            transaction.setCustomAnimations(android.R.anim.fade_in, android.R.anim.fade_out, android.R.anim.fade_in, android.R.anim.fade_out)
            transaction.addToBackStack("")
            transaction.replace(R.id.fragment_container, fragment, ActivityEditMap.FRAGMENT_TAG).commit()
        }
        container.addView(view)
    }

    private fun addEvent(container: LinearLayout, worldMap: WorldMap, mapTile: MapTile, event: WorldMap.Event? = null) {
        val view = LayoutInflater.from(context).inflate(R.layout.layout_event, container, false)
        val editText = view.findViewById<EditText>(R.id.edit_text)
        var event = event
        if(event==null) {
            event = WorldMap.Event()
            mapTile.events.add(event.eventId)
            worldMap.events.add(event)
        } else
            editText.setText(event.eventText)

        val eventId = view.findViewById<TextView>(R.id.event_id)
        eventId.text = event.eventId.toString()
        val isGlobalSwitch = view.findViewById<Switch>(R.id.is_global_switch)
        isGlobalSwitch.isChecked = event.isGlobal
        isGlobalSwitch.setOnCheckedChangeListener { view, isChecked ->
            event.isGlobal = isChecked
        }
        val probability = view.findViewById<EditText>(R.id.probability)
        probability.setText(event.probability.toString())
        probability.addTextChangedListener(object : TextWatcher {
            override fun afterTextChanged(s: Editable) {
                val toFloatOrNull = s.toString().toFloatOrNull()
                if (toFloatOrNull!=null) {
                    event.probability = toFloatOrNull
                }
            }

            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {}
            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {}

        })
        editText.addTextChangedListener(object : TextWatcher {
            override fun afterTextChanged(s: Editable) {
                event.eventText = s.toString()
            }

            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {}
            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {}

        })

        val removeButton = view.findViewById<Button>(R.id.remove)
        removeButton.setOnClickListener {
            worldMap.events.remove(event)
            mapTile.events.remove(event.eventId)
            container.removeView(view)
        }

        container.addView(view)
    }
}