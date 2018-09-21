package com.bottools.botcontentfiller.ui.events

import android.os.Bundle
import android.support.v4.app.ListFragment
import android.view.Menu
import android.view.MenuInflater
import android.view.MenuItem
import com.bottools.botcontentfiller.R
import com.bottools.botcontentfiller.manager.DatabaseManager
import com.bottools.botcontentfiller.model.Event
import com.bottools.botcontentfiller.ui.editmap.ActivityEditMap

class EventsListFragment: ListFragment() {

    private var events = ArrayList<Event>()
    private lateinit var adapter: EventsAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setHasOptionsMenu(true)
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        activity?.setTitle(R.string.events_list)
        events = DatabaseManager.getList()
        adapter = EventsAdapter(activity!!, {
            openEventTilesListFragment(it)
        }, {
            removeEvent(it)
        })
        adapter.addAll(events)
        listAdapter = adapter
    }

    override fun onCreateOptionsMenu(menu: Menu?, inflater: MenuInflater) {
        inflater.inflate(R.menu.plus_menu, menu)
        super.onCreateOptionsMenu(menu, inflater)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        return when (item.itemId) {
            R.id.plus -> {
                addEvent()
                true
            }
            else -> super.onOptionsItemSelected(item)
        }
    }

    private fun addEvent() {
        val event = Event()
        event.initEventId()
        DatabaseManager.save(event)
        openEventTilesListFragment(event)
    }

    private fun removeEvent(event: Event) {
        events.remove(event)
        adapter.remove(event)
        adapter.notifyDataSetChanged()
        DatabaseManager.remove<Event>(event.id)
    }

    private fun openEventTilesListFragment(event: Event) {
        val fragment = EditEventFragment.createInstance(event.id)
        val transaction = activity!!.supportFragmentManager.beginTransaction()
        transaction.setCustomAnimations(android.R.anim.fade_in, android.R.anim.fade_out, android.R.anim.fade_in, android.R.anim.fade_out)
        transaction.addToBackStack("")
        transaction.replace(com.bottools.botcontentfiller.R.id.fragment_container, fragment, ActivityEditMap.FRAGMENT_TAG).commit()
    }
}