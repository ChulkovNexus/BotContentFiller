package com.bottools.botcontentfiller.ui.events

import android.os.Bundle
import android.support.v4.app.ListFragment
import android.view.Menu
import android.view.MenuInflater
import android.view.MenuItem
import com.bottools.botcontentfiller.R
import com.bottools.botcontentfiller.manager.DatabaseManager
import com.bottools.botcontentfiller.model.Building
import com.bottools.botcontentfiller.model.Event
import com.bottools.botcontentfiller.ui.Buildings.BuildingsAdapter
import com.bottools.botcontentfiller.ui.editmap.ActivityEditMap
import com.bottools.botcontentfiller.utils.random

class BuildingsListFragment: ListFragment() {

    private var buildings = ArrayList<Building>()
    private lateinit var adapter: BuildingsAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setHasOptionsMenu(true)
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        activity?.setTitle(R.string.buildings_list)
        buildings = DatabaseManager.getList()
        adapter = BuildingsAdapter(activity!!, {
            openEventTilesListFragment(it)
        }, {
            removeEvent(it)
        })
        adapter.addAll(buildings)
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
        val building = Building()
        building.id = (0..Int.MAX_VALUE).random()
        DatabaseManager.save(building)
        openEventTilesListFragment(building)
    }

    private fun removeEvent(building: Building) {
        buildings.remove(building)
        adapter.remove(building)
        adapter.notifyDataSetChanged()
        DatabaseManager.remove<Event>(building.id)
    }

    private fun openEventTilesListFragment(building: Building) {
        val fragment = EditBuildingFragment.createInstance(building.id)
        val transaction = activity!!.supportFragmentManager.beginTransaction()
        transaction.setCustomAnimations(android.R.anim.fade_in, android.R.anim.fade_out, android.R.anim.fade_in, android.R.anim.fade_out)
        transaction.addToBackStack("")
        transaction.replace(com.bottools.botcontentfiller.R.id.fragment_container, fragment, ActivityEditMap.FRAGMENT_TAG).commit()
    }
}